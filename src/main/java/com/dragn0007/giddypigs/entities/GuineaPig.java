package com.dragn0007.giddypigs.entities;

import com.dragn0007.dragnlivestock.items.LOItems;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.entities.ai.GuineaPigFollowOwnerGoal;
import com.dragn0007.giddypigs.entities.ai.PiggieFollowLeaderGoal;
import com.dragn0007.giddypigs.entities.util.EntityTypes;
import com.dragn0007.giddypigs.util.GGPTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class GuineaPig extends TamableAnimal implements GeoEntity {

	private GuineaPig leader;
	private int herdSize = 1;

	public GuineaPig(EntityType<? extends GuineaPig> type, Level level) {
		super(type, level);
		this.noCulling = true;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 6.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.16F);
	}

	private static final Ingredient FOOD_ITEMS = Ingredient.of(GGPTags.Items.GUINEA_PIG_FOOD);

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.8F));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));

		this.goalSelector.addGoal(4, new PiggieFollowLeaderGoal(this));
		this.goalSelector.addGoal(6, new GuineaPigFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.WOLVES) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.CATS) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.DOGS) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity ->
				livingEntity.getType().is(LOTags.Entity_Types.FOXES) && (livingEntity instanceof TamableAnimal && !((TamableAnimal) livingEntity).isTame()) && !this.isTame()
		));
	}

	public boolean isFollower() {
		return this.leader != null && this.leader.isAlive();
	}

	public GuineaPig startFollowing(GuineaPig pig) {
		this.leader = pig;
		pig.addFollower();
		return pig;
	}

	public void stopFollowing() {
		this.leader.removeFollower();
		this.leader = null;
	}

	private void addFollower() {
		++this.herdSize;
	}

	private void removeFollower() {
		--this.herdSize;
	}

	public boolean canBeFollowed() {
		return this.hasFollowers() && this.herdSize < this.getMaxHerdSize();
	}

	public int getMaxHerdSize() {
		return 4;
	}

	public boolean hasFollowers() {
		return this.herdSize > 1;
	}

	public boolean inRangeOfLeader() {
		return this.distanceToSqr(this.leader) <= 121.0D;
	}

	public void pathToLeader() {
		if (this.isFollower()) {
			this.getNavigation().moveTo(this.leader, 1.0D);
		}

	}

	public void addFollowers(Stream<? extends GuineaPig> stream) {
		stream.limit((long)(this.getMaxHerdSize() - this.herdSize)).filter((pig) -> {
			return pig != this;
		}).forEach((pig) -> {
			pig.startFollowing(this);
		});
	}
	
	@Override
	public void tick() {
		super.tick();

		if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
			List<? extends GuineaPig> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
			if (list.size() <= 1) {
				this.herdSize = 1;
			}
		}
	}

	@Override
	public float getStepHeight() {
		return 1F;
	}

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
		double currentSpeed = this.getDeltaMovement().lengthSqr();
		double speedThreshold = 0.01;

		AnimationController<T> controller = tAnimationState.getController();

		if(tAnimationState.isMoving()) {
			if (currentSpeed > speedThreshold) {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(2.4);
			} else {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.4);
			}
		} else {
			if (isInSittingPose()) {
				controller.setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.0);
			} else {
				controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.0);
			}
		}

		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 2, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}


	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.RABBIT_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.RABBIT_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource p_30720_) {
		super.getHurtSound(p_30720_);
		return SoundEvents.RABBIT_HURT;
	}

	protected void playStepSound(BlockPos p_28254_, BlockState p_28255_) {
		this.playSound(SoundEvents.RABBIT_JUMP, 0.15F, 1.0F);
	}

	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();

		if (itemstack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isFemale()) {
			player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, LOItems.FEMALE_GENDER_TEST_STRIP.get().getDefaultInstance());
			player.setItemInHand(hand, itemstack1);
			return InteractionResult.SUCCESS;
		}

		if (itemstack.is(LOItems.GENDER_TEST_STRIP.get()) && this.isMale()) {
			player.playSound(SoundEvents.BEEHIVE_EXIT, 1.0F, 1.0F);
			ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, LOItems.MALE_GENDER_TEST_STRIP.get().getDefaultInstance());
			player.setItemInHand(hand, itemstack1);
			return InteractionResult.SUCCESS;
		}

		if (player.isShiftKeyDown() && !this.isFood(itemstack) && !this.isInSittingPose() && !this.wasToldToWander() && this.isOwnedBy(player)) {
			this.setToldToWander(true);
			player.displayClientMessage(Component.translatable("tooltip.giddypigs.wandering.tooltip").withStyle(ChatFormatting.GOLD), true);
			return InteractionResult.SUCCESS;
		}

		if (player.isShiftKeyDown() && !this.isFood(itemstack) && !this.isInSittingPose() && this.wasToldToWander() && this.isOwnedBy(player)) {
			this.setToldToWander(false);
			player.displayClientMessage(Component.translatable("tooltip.giddypigs.following.tooltip").withStyle(ChatFormatting.GOLD), true);
			return InteractionResult.SUCCESS;
		}

		if (this.isTame()) {
			if (this.isFood(itemstack)) {
				this.level().addParticle(ParticleTypes.HEART, this.getRandomX(0.6D), this.getRandomY(), this.getRandomZ(0.6D), 0.7D, 0.7D, 0.7D);

				if (this.getHealth() < this.getMaxHealth()) {
					this.heal((float) 2.0);
				}

				int i = this.getAge();
				if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
					this.usePlayerItem(player, hand, itemstack);
					this.setInLove(player);
					return InteractionResult.SUCCESS;
				}

				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				this.gameEvent(GameEvent.ENTITY_INTERACT);
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}

		if (this.level().isClientSide) {
			boolean flag = this.isOwnedBy(player) || this.isTame() || this.isFood(itemstack) && !this.isTame();
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else {
			if (this.isTame()) {
				if (this.isFood(itemstack)) {

					if (this.getHealth() < this.getMaxHealth()) {
						this.heal(2F);
					}

					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
					}

					int i = this.getAge();
					if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
						this.usePlayerItem(player, hand, itemstack);
						this.setInLove(player);
						return InteractionResult.SUCCESS;
					}

					return InteractionResult.SUCCESS;
				}

				if (!this.isFood(itemstack)) {
					InteractionResult interactionresult = super.mobInteract(player, hand);
					if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
						if (!player.isShiftKeyDown()) {
							this.setOrderedToSit(!this.isOrderedToSit());
							this.jumping = false;
							this.navigation.stop();
							this.setTarget((LivingEntity) null);
							return InteractionResult.SUCCESS;
						}
						return interactionresult;
					}
				}

			} else if (this.isFood(itemstack)) {
				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					this.tame(player);
					this.navigation.stop();
					this.setTarget((LivingEntity)null);
					this.setOrderedToSit(true);
					this.level().broadcastEntityEvent(this, (byte)7);
				} else {
					this.level().broadcastEntityEvent(this, (byte)6);
				}

				return InteractionResult.SUCCESS;
			}

			return super.mobInteract(player, hand);
		}
	}

	public boolean toldToWander = false;
	public boolean wasToldToWander() {
		return this.toldToWander;
	}
	public boolean getToldToWander() {
		return this.toldToWander;
	}
	public void setToldToWander(boolean toldToWander) {
		this.toldToWander = toldToWander;
	}

	// Generates the base texture
	private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OVERLAY = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> BREED = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> EYES = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);

	public ResourceLocation getTextureLocation() {
		return GuineaPigModel.Variant.variantFromOrdinal(getVariant()).resourceLocation;
	}
	public ResourceLocation getOverlayLocation() {
		return GuineaPigMarkingLayer.Overlay.overlayFromOrdinal(getOverlayVariant()).resourceLocation;
	}
	public ResourceLocation getEyeLocation() {
		return GuineaPigEyeLayer.Overlay.eyeFromOrdinal(getEyeVariant()).resourceLocation;
	}

	public int getVariant() {
		return this.entityData.get(VARIANT);
	}
	public int getOverlayVariant() {
		return this.entityData.get(OVERLAY);
	}
	public int getEyeVariant() {
		return this.entityData.get(EYES);
	}
	public int getBreed() {
		return this.entityData.get(BREED);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}
	public void setOverlayVariant(int overlayVariant) {
		this.entityData.set(OVERLAY, overlayVariant);
	}
	public void setEyeVariant(int eyeVariant) {
		this.entityData.set(EYES, eyeVariant);
	}
	public void setBreed(int breed) {
		this.entityData.set(BREED, breed);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("Variant")) {
			setVariant(tag.getInt("Variant"));
		}

		if (tag.contains("Overlay")) {
			setOverlayVariant(tag.getInt("Overlay"));
		}

		if (tag.contains("Eyes")) {
			setEyeVariant(tag.getInt("Eyes"));
		}

		if (tag.contains("Gender")) {
			this.setGender(tag.getInt("Gender"));
		}

		if (tag.contains("Breed")) {
			this.setBreed(tag.getInt("Breed"));
		}

		if (tag.contains("Wandering")) {
			this.setToldToWander(tag.getBoolean("Wandering"));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());
		tag.putInt("Overlay", getOverlayVariant());
		tag.putInt("Eyes", getEyeVariant());
		tag.putInt("Gender", this.getGender());
		tag.putInt("Breed", this.getBreed());
		tag.putBoolean("Wandering", this.getToldToWander());
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (data == null) {
			data = new AgeableMobGroupData(0.2F);
		}
		Random random = new Random();
		setGender(random.nextInt(Gender.values().length));

		if (LivestockOverhaulCommonConfig.SPAWN_BY_BREED.get()) {
			this.setColor();
			this.setMarking();
			this.setEyeColorByChance();
			this.setBreedChance();
		} else {
			this.setVariant(random.nextInt(GuineaPigModel.Variant.values().length));
			this.setOverlayVariant(random.nextInt(GuineaPigMarkingLayer.Overlay.values().length));
			this.setOverlayVariant(random.nextInt(GuineaPigEyeLayer.Overlay.values().length));
			this.setBreed(random.nextInt(Breed.values().length));
		}

		return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data, tag);
	}

	public void setColor() {
		if (random.nextDouble() <= 0.05) {
			int[] variants = {4, 5, 6, 9};
			int randomIndex = new Random().nextInt(variants.length);
			this.setVariant(variants[randomIndex]);
		} else if (random.nextDouble() > 0.05 && random.nextDouble() < 0.20) {
			int[] variants = {1, 8, 10, 11};
			int randomIndex = new Random().nextInt(variants.length);
			this.setVariant(variants[randomIndex]);
		} else if (random.nextDouble() > 0.20) {
			int[] variants = {0, 2, 3, 7};
			int randomIndex = new Random().nextInt(variants.length);
			this.setVariant(variants[randomIndex]);
		}
	}

	public void setMarking() {
		if (random.nextDouble() <= 0.03) {
			this.setOverlayVariant(random.nextInt(GuineaPigMarkingLayer.Overlay.values().length));
		} else if (random.nextDouble() > 0.03) {
			this.setOverlayVariant(0);
		}
	}

	public void setEyeColorByChance() {
		if (this.getVariant() == 4 || this.getVariant() == 11 || this.getOverlayVariant() == 73) {
			if (random.nextDouble() < 0.10) {
				this.setEyeVariant(3); //blue
			} else if (random.nextDouble() > 0.10) {
				this.setEyeVariant(this.getRandom().nextInt(2)); //random (between black and brown)
			} else {
				this.setEyeVariant(0);
			}
		} else {
			if (random.nextDouble() < 0.02) {
				this.setEyeVariant(3); //blue
			} else if (random.nextDouble() > 0.02) {
				this.setEyeVariant(this.getRandom().nextInt(2)); //random (between black and brown)
			} else {
				this.setEyeVariant(0);
			}
		}
	}

	public void setBreedChance() {
		if (random.nextDouble() <= 0.02) {
			this.setBreed(1);
		} else if (random.nextDouble() > 0.02) {
			this.setBreed(0);
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(OVERLAY, 0);
		this.entityData.define(EYES, 0);
		this.entityData.define(GENDER, 0);
		this.entityData.define(BREED, 0);
	}

	public enum Gender {
		FEMALE,
		MALE
	}
	public boolean isFemale() {
		return this.getGender() == 0;
	}
	public boolean isMale() {
		return this.getGender() == 1;
	}
	public static final EntityDataAccessor<Integer> GENDER = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);
	public int getGender() {
		return this.entityData.get(GENDER);
	}
	public void setGender(int gender) {
		this.entityData.set(GENDER, gender);
	}

	public boolean canParent() {
		return !this.isBaby() && this.isInLove();
	}

	public boolean canMate(Animal animal) {
		if (animal == this) {
			return false;
		} else if (!(animal instanceof GuineaPig)) {
			return false;
		} else {
			if (!LivestockOverhaulCommonConfig.GENDERS_AFFECT_BREEDING.get()) {
				return this.canParent() && ((GuineaPig) animal).canParent();
			} else {
				GuineaPig partner = (GuineaPig) animal;
				if (this.canParent() && partner.canParent() && this.getGender() != partner.getGender()) {
					return isFemale();
				}
			}
		}
		return false;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		GuineaPig baby = (GuineaPig) ageableMob;
		if (ageableMob instanceof GuineaPig) {
			GuineaPig partner = (GuineaPig) ageableMob;
			baby = EntityTypes.GUINEA_PIG_ENTITY.get().create(serverLevel);

			int i = this.random.nextInt(9);
			int variant;
			if (i < 4) {
				variant = this.getVariant();
			} else if (i < 8) {
				variant = partner.getVariant();
			} else {
				variant = this.random.nextInt(GuineaPigModel.Variant.values().length);
			}

			int j = this.random.nextInt(5);
			int overlay;
			if (j < 2) {
				overlay = this.getOverlayVariant();
			} else if (j < 4) {
				overlay = partner.getOverlayVariant();
			} else {
				overlay = this.random.nextInt(GuineaPigMarkingLayer.Overlay.values().length);
			}

			int l = this.random.nextInt(5);
			int eye;
			if (l < 2) {
				eye = this.getEyeVariant();
			} else if (l < 4) {
				eye = partner.getEyeVariant();
			} else {
				eye = this.random.nextInt(GuineaPigEyeLayer.Overlay.values().length);
			}

			int k = this.random.nextInt(5);
			int breed;
			if (k < 2) {
				breed = this.getBreed();
			} else if (k < 4) {
				breed = partner.getBreed();
			} else {
				breed = this.random.nextInt(Breed.values().length);
			}

			baby.setVariant(variant);
			baby.setOverlayVariant(overlay);
			baby.setEyeVariant(eye);
			baby.setBreed(breed);
			baby.setGender(random.nextInt(Gender.values().length));
		}

		return baby;
	}

	public enum Breed {
		DEFAULT(new ResourceLocation(GiddyGuineaPigs.MODID, "geo/guinea_pig.geo.json")),
		FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "geo/fluffy.geo.json"));

		public final ResourceLocation resourceLocation;

		Breed(ResourceLocation resourceLocation) {
			this.resourceLocation = resourceLocation;
		}

		public static Breed breedFromOrdinal(int ordinal) {
			return Breed.values()[ordinal % Breed.values().length];
		}
	}
}

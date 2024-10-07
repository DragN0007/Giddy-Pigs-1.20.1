package com.dragn0007.giddypigs.entities;

import com.dragn0007.giddypigs.entities.ai.PiggieFollowLeaderGoal;
import com.dragn0007.giddypigs.entities.util.EntityTypes;
import com.dragn0007.giddypigs.util.GGPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;
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
				.add(Attributes.MAX_HEALTH, 8.0D)
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

		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 15.0F, 1.8F, 1.8F, livingEntity
				-> livingEntity instanceof Wolf
				|| livingEntity instanceof Cat
				|| livingEntity instanceof Ocelot
				//don't worry, these animals don't actually hunt piggies. they're just cautious around them!
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
				controller.setAnimationSpeed(2.2);
			} else {
				controller.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
				controller.setAnimationSpeed(1.2);
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
		ItemStack itemStack = player.getItemInHand(hand);
		if (this.isTame()) {
			if (this.isFood(itemStack)) {
				if (this.getHealth() < this.getMaxHealth()) {
					// heal
					this.usePlayerItem(player, hand, itemStack);
					this.heal(itemStack.getFoodProperties(this).getNutrition());
					this.gameEvent(GameEvent.ENTITY_INTERACT);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				} else if (this.canFallInLove() && !this.level().isClientSide) {
					// love mode
					this.usePlayerItem(player, hand, itemStack);
					this.setInLove(player);
					this.gameEvent(GameEvent.ENTITY_INTERACT);
					return InteractionResult.SUCCESS;
				}
			} else if (player.isCrouching()) {
				// sit
				this.setOrderedToSit(!this.isOrderedToSit());
				return InteractionResult.SUCCESS;
			}
		} else if (this.isFood(itemStack) && !this.level().isClientSide) {
			this.usePlayerItem(player, hand, itemStack);
			// try to tame (33% chance to succeed)
			if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
				this.tame(player);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.sidedSuccess(this.level().isClientSide);
	}

	// Generates the base texture
	public ResourceLocation getTextureLocation() {
		return GuineaPigModel.Variant.variantFromOrdinal(getVariant()).resourceLocation;
	}

	public ResourceLocation getOverlayLocation() {
		return GuineaPigMarkingLayer.Overlay.overlayFromOrdinal(getOverlayVariant()).resourceLocation;
	}

	private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> OVERLAY = SynchedEntityData.defineId(GuineaPig.class, EntityDataSerializers.INT);

	public int getVariant() {
		return this.entityData.get(VARIANT);
	}
	public int getOverlayVariant() {
		return this.entityData.get(OVERLAY);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}
	public void setOverlayVariant(int overlayVariant) {
		this.entityData.set(OVERLAY, overlayVariant);
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
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", getVariant());

		tag.putInt("Overlay", getOverlayVariant());
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (data == null) {
			data = new AgeableMobGroupData(0.2F);
		}
		Random random = new Random();
		setVariant(random.nextInt(GuineaPigModel.Variant.values().length));
		setOverlayVariant(random.nextInt(GuineaPigMarkingLayer.Overlay.values().length));

		return super.finalizeSpawn(serverLevelAccessor, instance, spawnType, data, tag);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(OVERLAY, 0);
	}

	protected boolean canParent() {
		return !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
	}

	public boolean canMate(Animal animal) {
			return this.canParent() && ((GuineaPig) animal).canParent();
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		GuineaPig guineaPig = (GuineaPig) ageableMob;
		if (ageableMob instanceof GuineaPig) {
			GuineaPig guineaPig1 = (GuineaPig) ageableMob;
			guineaPig = EntityTypes.GUINEA_PIG_ENTITY.get().create(serverLevel);

			int i = this.random.nextInt(9);
			int variant;
			if (i < 4) {
				variant = this.getVariant();
			} else if (i < 8) {
				variant = guineaPig1.getVariant();
			} else {
				variant = this.random.nextInt(GuineaPigModel.Variant.values().length);
			}

			int j = this.random.nextInt(5);
			int overlay;
			if (j < 2) {
				overlay = this.getOverlayVariant();
			} else if (j < 4) {
				overlay = guineaPig1.getOverlayVariant();
			} else {
				overlay = this.random.nextInt(GuineaPigMarkingLayer.Overlay.values().length);
			}

			((GuineaPig) guineaPig).setVariant(variant);
			((GuineaPig) guineaPig).setOverlayVariant(overlay);
		}

		return guineaPig;
	}
}

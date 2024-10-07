package com.dragn0007.giddypigs.entities.ai;

import com.dragn0007.giddypigs.entities.GuineaPig;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class PiggieFollowLeaderGoal extends Goal {
   private static final int INTERVAL_TICKS = 200;
   private final GuineaPig mob;
   private int timeToRecalcPath;
   private int nextStartTick;

   public PiggieFollowLeaderGoal(GuineaPig guineaPig) {
      this.mob = guineaPig;
      this.nextStartTick = this.nextStartTick(guineaPig);
   }

   protected int nextStartTick(GuineaPig pig) {
      return reducedTickDelay(200 + pig.getRandom().nextInt(200) % 20);
   }

   public boolean canUse() {
      if (this.mob.hasFollowers()) {
         return false;
      } else if (this.mob.isFollower()) {
         return true;
      } else if (this.nextStartTick > 0) {
         --this.nextStartTick;
         return false;
      } else {
         this.nextStartTick = this.nextStartTick(this.mob);
         Predicate<GuineaPig> predicate = (follower) -> {
            return follower.canBeFollowed() || !follower.isFollower();
         };
         List<? extends GuineaPig> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
         GuineaPig GuineaPig = DataFixUtils.orElse(list.stream().filter(com.dragn0007.giddypigs.entities.GuineaPig::canBeFollowed).findAny(), this.mob);
         GuineaPig.addFollowers(list.stream().filter((guineaPig) -> {
            return !guineaPig.isFollower();
         }));
         return this.mob.isFollower();
      }
   }

   public boolean canContinueToUse() {
      return this.mob.isFollower() && this.mob.inRangeOfLeader();
   }

   public void start() {
      this.timeToRecalcPath = 0;
   }

   public void stop() {
      this.mob.stopFollowing();
   }

   public void tick() {
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = this.adjustedTickDelay(10);
         this.mob.pathToLeader();
      }
   }
}
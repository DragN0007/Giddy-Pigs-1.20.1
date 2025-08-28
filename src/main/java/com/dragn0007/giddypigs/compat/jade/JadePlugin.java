package com.dragn0007.giddypigs.compat.jade;

import com.dragn0007.giddypigs.compat.jade.breed.PiggieBreedTooltip;
import com.dragn0007.giddypigs.compat.jade.gender.PiggieGenderTooltip;
import com.dragn0007.giddypigs.entities.GuineaPig;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(new PiggieGenderTooltip(), GuineaPig.class);
        registration.registerEntityComponent(new PiggieBreedTooltip(), GuineaPig.class);
    }
}

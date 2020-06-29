/*
 * Copyright 2020 The Terasology Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.destinationsol.body.systems;

import org.destinationsol.body.components.BodyLinked;
import org.destinationsol.body.events.BodyUpdateEvent;
import org.destinationsol.common.In;
import org.destinationsol.entitysystem.EntitySystemManager;
import org.destinationsol.game.SolGame;
import org.destinationsol.game.UpdateAwareSystem;

/**
 * Sends a {@link BodyUpdateEvent} every tick to each entity with a {@link BodyLinked} component.
 */
public class BodyHandlerUpdateSystem implements UpdateAwareSystem {

    @In
    private EntitySystemManager entitySystemManager;

    @Override
    public void update(SolGame game, float timeStep) {
        entitySystemManager.sendEvent(new BodyUpdateEvent(), new BodyLinked());
    }
}

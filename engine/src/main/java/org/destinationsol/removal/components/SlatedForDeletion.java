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
package org.destinationsol.removal.components;

import com.badlogic.gdx.physics.box2d.Body;
import org.destinationsol.removal.events.DeletionEvent;
import org.destinationsol.removal.systems.DeletionUpdateSystem;
import org.destinationsol.removal.systems.DestructionSystem;
import org.terasology.gestalt.entitysystem.component.Component;

/**
 * Indicates that an entity can safely be deleted.
 * <p>
 * Every tick, the {@link DeletionUpdateSystem} sends a {@link DeletionEvent} to each entity with a SlatedForDeletion
 * component, which causes the entity to actually be deleted by the {@link DestructionSystem}. This is done so that
 * certain resources, such as libGDX's {@link Body} class, can be disposed of properly before the entity is deleted.
 */
public class SlatedForDeletion implements Component<SlatedForDeletion> {
    @Override
    public void copy(SlatedForDeletion other) {

    }
}
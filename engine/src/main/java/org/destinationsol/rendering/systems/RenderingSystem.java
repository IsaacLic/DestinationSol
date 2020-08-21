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
package org.destinationsol.rendering.systems;

import com.badlogic.gdx.math.Vector2;
import org.destinationsol.common.In;
import org.destinationsol.rendering.RenderableElement;
import org.destinationsol.rendering.components.Renderable;
import org.destinationsol.rendering.events.RenderEvent;
import org.destinationsol.entitysystem.EntitySystemManager;
import org.destinationsol.entitysystem.EventReceiver;
import org.destinationsol.game.GameDrawer;
import org.destinationsol.location.components.Angle;
import org.destinationsol.location.components.Position;
import org.destinationsol.size.components.Size;
import org.terasology.gestalt.entitysystem.entity.EntityRef;
import org.terasology.gestalt.entitysystem.event.EventResult;
import org.terasology.gestalt.entitysystem.event.ReceiveEvent;

/**
 * This handles the drawing of each entity with a {@link Renderable} component.
 */
public class RenderingSystem implements EventReceiver {

    @In
    private EntitySystemManager entitySystemManager;

    @In
    private GameDrawer drawer;

    @ReceiveEvent(components = {Renderable.class, Position.class, Size.class})
    public EventResult onRender(RenderEvent event, EntityRef entity) {

        Renderable renderable = entity.getComponent(Renderable.class).get();
        if (!renderable.isInvisible) {

            Vector2 basePosition = entity.getComponent(Position.class).get().position;
            float size = entity.getComponent(Size.class).get().size;

            float baseAngle = 0;
            if (entity.hasComponent(Position.class)) {
                baseAngle = entity.getComponent(Angle.class).get().getAngle();
            }

            for (RenderableElement renderableElement : renderable.elements) {
                float angle = renderableElement.relativeAngle + baseAngle;
                Vector2 renderableElementPosition = basePosition.add(renderableElement.relativePosition);

                /*
                This calculates how much the position of the drawable should be shifted horizontally or vertically to
                line up with the collision mesh.
                 */
                float horizontalShift = renderableElement.getWidth() / 2;
                float verticalShift = renderableElement.getHeight() / 2;
                horizontalShift += renderableElement.graphicsOffset.x * size;
                verticalShift += renderableElement.graphicsOffset.y * size;

                drawer.draw(renderableElement.texture, renderableElement.getWidth(), renderableElement.getHeight(),
                        horizontalShift, verticalShift, renderableElementPosition.x, renderableElementPosition.y, angle,
                        renderableElement.tint);
            }
        }

        return EventResult.CONTINUE;
    }
}

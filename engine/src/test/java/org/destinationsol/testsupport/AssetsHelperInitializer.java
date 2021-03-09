package org.destinationsol.testsupport;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import org.destinationsol.assets.AssetHelper;
import org.destinationsol.assets.Assets;
import org.destinationsol.modules.ModuleManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.terasology.gestalt.entitysystem.component.management.ComponentManager;

/**
 * Create partial game instance with {@link ModuleManager}, {@link Assets}, {@link ComponentManager}, {@link HeadlessApplication}
 * <p>
 * There classes needs for {@link Assets} works.
 * <p>
 * Use it for tests, which used {@link org.destinationsol.assets.Assets}
 * <p>
 * {@link ModuleManager} and {@link ComponentManager} needs for {@link AssetHelper}
  * <p>
 * {@link AssetHelper} needs for initialize {@link Assets}
 * <p>
 * {@link HeadlessApplication} needs for filling {@link com.badlogic.gdx.Gdx#app}. otherwise we can catch {@link NullPointerException} in some places of engine.
 * <p>
 * Note: Using {@link ResourceLock} for concurrency locking tests, which using this Interface, because {@link Assets} and {@link com.badlogic.gdx.Gdx} have static state.
 * <p>
 * Note 2: if you want provide another `engine`'s core classes/managers - better  extends this class.
 */
@ResourceLock("AssetsHelper")
public interface AssetsHelperInitializer {
    ThreadLocal<TestGameStateObject> state = new ThreadLocal<>();

    @BeforeEach
    default void initAssets() throws Exception {
        TestGameStateObject stateObject = new TestGameStateObject();
        state.set(stateObject);

        HeadlessApplication mockApplication = new HeadlessApplication(new ApplicationAdapter() {
        });
        stateObject.setApplication(mockApplication);

        ComponentManager componentManager = new ComponentManager();
        stateObject.setComponentManager(componentManager);

        ModuleManager moduleManager = new ModuleManager();
        moduleManager.init();
        stateObject.setModuleManager(moduleManager);

        AssetHelper helper = new AssetHelper();
        helper.init(moduleManager.getEnvironment(), componentManager, false);
        Assets.initialize(helper);
    }

    default ModuleManager getModuleManager() {
        return state.get().getModuleManager();
    }

    default Application getApplication() {
        return state.get().getApplication();
    }

    default ComponentManager getComponentManager() {
        return state.get().getComponentManager();
    }

    @AfterEach
    default void cleanupAssets() {
        state.remove();
    }

}

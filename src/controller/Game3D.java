package controller;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class Game3D extends SimpleApplication {

    @Override
    public void simpleInitApp() {
        // Charger le modèle 3D
        assetManager.registerLocator("/home/diakite/Bureau/Jmonkey/blender_file/obj/grand-carree-jaune.obj", FileLocator.class);
        Geometry testModel = (Geometry) assetManager.loadModel("test.obj");

        // Appliquer un matériau au modèle
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        testModel.setMaterial(mat);

        // Ajouter le modèle à la scène
        rootNode.attachChild(testModel);
    }

    public static void main(String[] args) {
        Game3D app = new Game3D();
        app.start();
    }
}
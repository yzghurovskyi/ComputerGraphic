package lab4;

import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.geometry.Sphere;
import java.applet.Applet;

public class Solar extends Applet {
    private Container frame;
    private BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 100.0);

    public static void main ( String[] args ) {
        Solar s = new Solar();
        Frame f = new Frame ();
        f.setSize(1366, 900);
        // set window properties
        f.addWindowListener (new WindowAdapter () {
            public void windowClosing (WindowEvent e) {
                System.exit (0);
            }
        });
        s.frame = f;
        s.display();
    }

    public void init() {
        frame = this;
        display();

        System.out.println("Init Done");
    }

    public void display() {
        Transform3D mainTrans = new Transform3D();
        mainTrans.setTranslation(new Vector3d(0,0,-10));				// move the viewer back
        TransformGroup mainGroup = new TransformGroup (mainTrans);

        // --------------- add sun globe ------------------
        TransformGroup sunGroup = new TransformGroup();
        createSun(sunGroup);
        mainGroup.addChild(sunGroup);

        /// --------------- add mercury globe -----------------
        Transform3D mercuryRotTrans = new Transform3D ();
        TransformGroup mercuryRotGroup = new TransformGroup(mercuryRotTrans);
        Alpha mercuryAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,12000,0,0,0,0,0);
        RotationInterpolator mercuryRotator =  new RotationInterpolator(mercuryAlpha, mercuryRotGroup);
        mercuryRotator.setSchedulingBounds(bounds);
        mercuryRotGroup.addChild(mercuryRotator);
        mercuryRotGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(mercuryRotGroup);

        Transform3D mercuryTrans = new Transform3D();
        mercuryTrans.setTranslation(new Vector3d(0,0,0.9));
        TransformGroup mercuryGroup = new TransformGroup(mercuryTrans);

        createMercury(mercuryGroup);
        mercuryRotGroup.addChild(mercuryGroup);

        // --------------- add venus globe -----------------
        Transform3D venusRotTrans = new Transform3D ();
        TransformGroup venusRotGroup = new TransformGroup(venusRotTrans);
        Alpha venusAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,28800,0,0,0,0,0);
        RotationInterpolator venusRotator =  new RotationInterpolator(venusAlpha, venusRotGroup);
        venusRotator.setSchedulingBounds(bounds);
        venusRotGroup.addChild(venusRotator);
        venusRotGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(venusRotGroup);

        Transform3D venusTrans = new Transform3D();
        venusTrans.setTranslation(new Vector3d(0,0,-1.3));
        TransformGroup venusGroup = new TransformGroup(venusTrans);

        createVenus(venusGroup);
        mercuryRotGroup.addChild(venusGroup);

        // --------------- add earth globe -----------------
        Transform3D earthRotTrans = new Transform3D ();
        TransformGroup earthRotGroup = new TransformGroup(earthRotTrans);
        Alpha earthAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,48000,0,0,0,0,0);
        RotationInterpolator rotator =  new RotationInterpolator(earthAlpha, earthRotGroup);
        rotator.setSchedulingBounds(bounds);
        earthRotGroup.addChild(rotator);
        earthRotGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(earthRotGroup);

        Transform3D earthTrans = new Transform3D();
        earthTrans.setTranslation(new Vector3d(0,0,-4));
        TransformGroup earthGroup = new TransformGroup(earthTrans);

        createEarth(earthGroup);
        earthRotGroup.addChild(earthGroup);

        // ------------------- add moon globe -------------------
        Transform3D firstMoonTrans = new Transform3D ();
        firstMoonTrans.rotX(1);
        TransformGroup firstMoonGroup = new TransformGroup(firstMoonTrans);
        earthGroup.addChild(firstMoonGroup);

        Transform3D moonRotTrans = new Transform3D ();
        TransformGroup moonRotGroup = new TransformGroup(moonRotTrans);

        Alpha moonAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,4000,0,0,0,0,0);
        RotationInterpolator moonRotator =  new RotationInterpolator(moonAlpha, moonRotGroup);
        moonRotator.setSchedulingBounds(bounds);
        moonRotGroup.addChild(moonRotator);
        moonRotGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        firstMoonGroup.addChild(moonRotGroup);


        Transform3D moonTrans = new Transform3D();
        moonTrans.setTranslation(new Vector3d(0,0,-0.1));
        TransformGroup moonGroup = new TransformGroup(moonTrans);
        createMoon(moonGroup);
        moonRotGroup.addChild(moonGroup);


        // create some background illumination
        AmbientLight starLight = new AmbientLight(true, new Color3f(0.4f,0.4f,0.4f));
        starLight.setInfluencingBounds(bounds);
        mainGroup.addChild(starLight);

        // --------------- add mars globe -----------------
        Transform3D masRotTrans = new Transform3D ();
        TransformGroup marsTransGroup = new TransformGroup(masRotTrans);
        Alpha marsAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,90000,0,0,0,0,0);
        RotationInterpolator marsRotator =  new RotationInterpolator(marsAlpha, marsTransGroup);
        marsRotator.setSchedulingBounds(bounds);
        marsTransGroup.addChild(marsRotator);
        marsTransGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(marsTransGroup);

        Transform3D marsTrans = new Transform3D();
        marsTrans.setTranslation(new Vector3d(0, 0,-2.1));
        TransformGroup marsGroup = new TransformGroup(marsTrans);

        createMars(marsGroup);
        marsTransGroup.addChild(marsGroup);

        // --------------- add jupiter globe -----------------
        Transform3D jupiterRotTrans = new Transform3D ();
        TransformGroup jupiterTransGroup = new TransformGroup(jupiterRotTrans);
        Alpha jupiterAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,480000,0,0,0,0,0);
        RotationInterpolator jupiterRotator =  new RotationInterpolator(jupiterAlpha, jupiterTransGroup);
        jupiterRotator.setSchedulingBounds(bounds);
        jupiterTransGroup.addChild(jupiterRotator);
        jupiterTransGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(jupiterTransGroup);

        Transform3D jupiterTrans = new Transform3D();
        jupiterTrans.setTranslation(new Vector3d(0,0,-5));
        TransformGroup jupiterGroup = new TransformGroup(jupiterTrans);

        createJupiter(jupiterGroup);
        jupiterTransGroup.addChild(jupiterGroup);

        // --------------- add saturn globe -----------------
        Transform3D saturnRotTrans = new Transform3D ();
        TransformGroup saturnTransGroup = new TransformGroup(saturnRotTrans);
        Alpha saturnAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,1440000,0,0,0,0,0);
        RotationInterpolator saturnRotation =  new RotationInterpolator(saturnAlpha, saturnTransGroup);
        saturnRotation.setSchedulingBounds(bounds);
        saturnTransGroup.addChild(saturnRotation);
        saturnTransGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(saturnTransGroup);

        Transform3D saturnTrans = new Transform3D();
        saturnTrans.setTranslation(new Vector3d(0,0,-9));
        TransformGroup saturnGroup = new TransformGroup(saturnTrans);

        createSaturn(saturnGroup);
        saturnTransGroup.addChild(saturnGroup);

        // --------------- add neptune globe -----------------
        Transform3D neptuneRotTrans = new Transform3D ();
        TransformGroup neptuneTransGroup = new TransformGroup(neptuneRotTrans);
        Alpha neptuneAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,8064000,0,0,0,0,0);
        RotationInterpolator neptuneRotator =  new RotationInterpolator(neptuneAlpha, neptuneTransGroup);
        neptuneRotator.setSchedulingBounds(bounds);
        neptuneTransGroup.addChild(neptuneRotator);
        neptuneTransGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(neptuneTransGroup);

        Transform3D neptuneTrans = new Transform3D();
        neptuneTrans.setTranslation(new Vector3d(0,0,-30));
        TransformGroup neptuneGroup = new TransformGroup(neptuneTrans);

        createNeptune(neptuneGroup);
        neptuneTransGroup.addChild(neptuneGroup);

        // --------------- add uranus globe -----------------
        Transform3D uranusRotTrans = new Transform3D ();
        TransformGroup uranusRotGroup = new TransformGroup(uranusRotTrans);
        Alpha uranusAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,4032000,0,0,0,0,0);
        RotationInterpolator uranusRotator =  new RotationInterpolator(uranusAlpha, uranusRotGroup);
        uranusRotator.setSchedulingBounds(bounds);
        uranusRotGroup.addChild(uranusRotator );
        uranusRotGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(uranusRotGroup);

        Transform3D uranusTrans = new Transform3D();
        uranusTrans.setTranslation(new Vector3d(0,0,-19));
        TransformGroup uranusGroup = new TransformGroup(uranusTrans);

        createUranus(uranusGroup);
        uranusRotGroup.addChild(uranusGroup);

        // --------------- add pluto globe -----------------
        Transform3D plutoRotTrans = new Transform3D ();
        TransformGroup plutoRotGroup = new TransformGroup(plutoRotTrans);
        Alpha plutoAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,11904000,0,0,0,0,0);
        RotationInterpolator plutoRotator =  new RotationInterpolator(plutoAlpha, plutoRotGroup);
        plutoRotator.setSchedulingBounds(bounds);
        plutoRotGroup.addChild(plutoRotator);
        plutoRotGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
        sunGroup.addChild(plutoRotGroup);

        Transform3D plutoTrans = new Transform3D();
        plutoTrans.setTranslation(new Vector3d(-0.2,-0.2,-36));
        TransformGroup plutoGroup = new TransformGroup(plutoTrans);

        createPluto(plutoGroup);
        plutoRotGroup.addChild(plutoGroup);

        // -------------- mouse movements ----------------------

        // allow runtime modification
        mainGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
        mainGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);

        // rotate with mouse
        MouseRotate rotate = new MouseRotate(mainGroup);
        rotate.setFactor (0.02);
        rotate.setSchedulingBounds (bounds);
        mainGroup.addChild(rotate);

        // zoom with mouse
        MouseZoom zoom = new MouseZoom(mainGroup);
        zoom.setFactor(0.02);
        zoom.setSchedulingBounds (bounds);
        mainGroup.addChild (zoom);

        // move with mouse
        MouseTranslate translate = new MouseTranslate(mainGroup);
        translate.setFactor(0.02);
        translate.setSchedulingBounds (bounds);
        mainGroup.addChild(translate);

        // create root of branch graph
        BranchGroup root = new BranchGroup ();
        root.addChild (mainGroup);
        root.compile();

        // init universe
        Canvas3D canvas = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse universe = new SimpleUniverse (canvas);
        universe.addBranchGraph (root);
        universe.getViewingPlatform ().setNominalViewingTransform ();

        canvas.resize(20,20);

        frame.add(BorderLayout.CENTER, canvas);

        frame.setVisible(true);
    }

    public void createPlanet(float planetSize, int selfRotSpeed, String textureFile, TransformGroup group, boolean emit) {
        TransformGroup lastGroup;
        if (selfRotSpeed != 0) {
            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
            Transform3D rotTrans = new Transform3D ();
            TransformGroup rotGroup = new TransformGroup(rotTrans);
            Alpha rotAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,selfRotSpeed,0,0,0,0,0);
            RotationInterpolator rotator = new RotationInterpolator(rotAlpha, rotGroup);
            rotator.setSchedulingBounds(bounds);
            rotGroup.addChild(rotator);
            rotGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
            group.addChild(rotGroup);
            lastGroup = rotGroup;
        } else {
            lastGroup = group;
        }

        Sphere planet = new Sphere (planetSize, Sphere.GENERATE_NORMALS+Sphere.GENERATE_TEXTURE_COORDS, 25, Utils.loadTexture(textureFile,emit));
        lastGroup.addChild (planet);

        if (emit) {
            PointLight sunLight = new PointLight(true,  new Color3f(1.0f, 1.0f, 1.0f),
                    new Point3f(0.0f, 0.0f, 0.0f),
                    new Point3f(1.0f, 0.0f, 0.0f));
            sunLight.setInfluencingBounds(bounds);
            lastGroup.addChild(sunLight);
        }
    }

    private void createSun(TransformGroup group) { createPlanet(0.5f,100000,"source\\sunmap.jpg",group, true); }

    private void createEarth(TransformGroup group) { createPlanet(0.08f,1000,"source\\earthmap1k.jpg",group, false); }

    private void createMoon(TransformGroup group) {createPlanet(0.02f,0,"source\\moonmap1k.jpg",group, false);}

    private void createMercury(TransformGroup group) {createPlanet(0.04f, 30, "source\\mercurymap.jpg", group, false);}

    private void createVenus(TransformGroup group) {createPlanet(0.08f, 10, "source\\venusmap.jpg", group, false);}

    private void createMars(TransformGroup group) {createPlanet(0.05f, 1000, "source\\marsmap1k.jpg", group, false);}

    private void createJupiter(TransformGroup group) {createPlanet(0.4f, 2400, "source\\jupitermap.jpg", group, false);}

    private void createSaturn(TransformGroup group) {createPlanet(0.35f, 2200, "source\\saturnmap.jpg", group, false);}

    private void createUranus(TransformGroup group) {createPlanet(0.2f, 1250, "source\\uranusmap.jpg", group, false);}

    private void createNeptune(TransformGroup group) {createPlanet(0.2f, 1350, "source\\neptunemap.jpg", group, false);}

    private void createPluto(TransformGroup group) {createPlanet(0.03f, 100, "source\\plutomap1k.jpg", group, false);}
}
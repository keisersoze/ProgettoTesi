package app.factory;

public class DeploymentTypes {
    public static final String BaseDeployment = "Deploy Random";
    public static final String LayerDeployment = "Deploy a strati";
    public static final String LayerProportionalDeployment = "Deploy a strati (inv. prop.)";

    public static String[] getDeploymentTypes(){
        return new String[]{BaseDeployment, LayerDeployment, LayerProportionalDeployment};
    }
}

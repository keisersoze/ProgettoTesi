package app.factory;

public class DeploymentTypes {
    public static final String BaseDeployment = "Deply Random";
    public static final String LayerDeployment = "Deploy a strati";
    public static final String LayerProportionalDeployment = "Depoloy a strati (inv. prop.)";

    public static String[] getDeploymentTypes(){
        return new String[]{BaseDeployment, LayerDeployment, LayerProportionalDeployment};
    }
}

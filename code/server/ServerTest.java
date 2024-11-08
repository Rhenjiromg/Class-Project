//include this everywhere you are. Include the root folder (client, resouces, server)
package server;

//include this to access the class ResourcesTest in resources folder. 
//also works: resources.* for all imports (if there are many imports)
import resources.ResourcesTest;

public class ServerTest{

    public static void main(String[] args) {
        ResourcesTest.testGetResources();
    }
}
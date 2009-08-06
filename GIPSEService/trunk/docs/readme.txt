Steps for using this service. First keep in mind that most documentation is at: http://sites.google.com/site/phgrid/Home/service-registry/gipse-biosense

Basically, 
1) you will need to rename gipse.properties.template to gipse.properties. 
2) Configure properties based on the instructions in gipse.properties
3) Run "ant createDeploymentGar" to create a configured gar for your environment
4) Deploy the gar to your globus environment (I test with globus-start-container -nosec) (if you're running on the same box as the build, you can use "ant deployGlobus" or "ant deployTomcat" as a shortcut)
5) Run "ant test" to make sure all the db config and service operations work
6) (optional) for custom tests or debugging, edit GIPSEServiceRequest-ForClient.xml with your query, run "ant runClient" to retrieve values, etc.
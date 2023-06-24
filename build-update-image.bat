echo "           STAGE 1 - PREPARE NATIVE IMAGE         \n"
CALL .\gradlew bootBuildImage
echo "           STAGE 2 - UPLOAD NATIVE IMAGE TO MINIKUBE"
CALL minikube image load quark-monolith-reactive:0.0.1-SNAPSHOT
echo DONE!
echo "           STAGE 3 - DELETE RUNNING POD(s)"
CALL kubectl delete pod -l app=quark

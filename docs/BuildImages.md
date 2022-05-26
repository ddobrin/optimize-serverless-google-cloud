gcr.io/dan-workshop-project-332213/quotes-jvm

./mvnw clean package -Pjvm-image -Dspring-boot.build-image.imageName=gcr.io/dan-workshop-project-332213/quotes-jvm:latest
./mvnw clean package -Pnative-image -Dspring-boot.build-image.imageName=gcr.io/dan-workshop-project-332213/quotes-native:latest

./mvnw clean package -Pjvm-image -Dspring-boot.build-image.imageName=gcr.io/dan-workshop-project-332213/bff-jvm:latest
./mvnw clean package -Pnative-image -Dspring-boot.build-image.imageName=gcr.io/dan-workshop-project-332213/bff-native:latest

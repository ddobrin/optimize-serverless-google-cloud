gcr.io/dan-workshop-project-332213/quotes-jvm

./mvnw clean package -Pjvm-image && docker tag quotes-jvm gcr.io/dan-workshop-project-332213/quotes-jvm && docker push gcr.io/dan-workshop-project-332213/quotes-jvm

./mvnw clean package -Pnative-image -DskipTests && docker tag quotes-native gcr.io/dan-workshop-project-332213/quotes-native && docker push gcr.io/dan-workshop-project-332213/quotes-native

./mvnw clean package -Pjvm-image && docker tag bff-jvm gcr.io/dan-workshop-project-332213/bff-jvm && docker push gcr.io/dan-workshop-project-332213/bff-jvm

<!-- ./mvnw clean package -Pnative-image -DskipTests && docker tag quotes-native gcr.io/dan-workshop-project-332213/quotes-native && docker push gcr.io/dan-workshop-project-332213/quotes-native -->

./mvnw clean package -Pjvm-image && docker tag reference-jvm gcr.io/dan-workshop-project-332213/reference-jvm && docker push gcr.io/dan-workshop-project-332213/reference-jvm

./mvnw clean package -Pnative-image && docker tag reference-native gcr.io/dan-workshop-project-332213/reference-native && docker push gcr.io/dan-workshop-project-332213/reference-native



./mvnw clean package -Pjvm-image 
./mvnw clean package -Pnative-image 

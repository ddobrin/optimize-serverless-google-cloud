  gcloud run deploy quotes --image gcr.io/dan-workshop-project-332213/quotes-jvm \
  --add-cloudsql-instances quote-cr-db-instance \
  --set-env-vars INSTANCE_CONNECTION_NAME="quote-cr-db-instance" \
  --set-env-vars DB_NAME="quote_db" \
  --set-env-vars DB_USER="quote_user" \
  --set-env-vars DB_PASS="quote-cr-db-pwd" \
  --set-env-vars INSTANCE_HOST="172.24.0.10" \
  --set-env-vars DB_HOST="172.24.0.10" \
  --set-env-vars DB_PORT="5432" \
  --set-env-vars SERVER.PORT="8080" \
  --set-env-vars SPRING_PROFILES_ACTIVE="cloud-dev" \
  --set-env-vars TARGET="Cloud Run - Postgres 14 - Java 17" \
  --vpc-connector="quote-connector" \
  --region us-central1 \
  --memory 2Gi \
  --service-account cr-quote-service-account \
  --no-allow-unauthenticated

  curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://quotes-ieuwkt6jkq-uc.a.run.app

  TOKEN=$(gcloud auth print-identity-token)
  http -A bearer -a $TOKEN https://quotes-ieuwkt6jkq-uc.a.run.app


  gcloud run deploy quotes-native --image gcr.io/dan-workshop-project-332213/quotes-native \
  --add-cloudsql-instances quote-cr-db-instance \
  --set-env-vars INSTANCE_CONNECTION_NAME="quote-cr-db-instance" \
  --set-env-vars DB_NAME="quote_db" \
  --set-env-vars DB_USER="quote_user" \
  --set-env-vars DB_PASS="quote-cr-db-pwd" \
  --set-env-vars INSTANCE_HOST="172.24.0.10" \
  --set-env-vars DB_HOST="172.24.0.10" \
  --set-env-vars DB_PORT="5432" \
  --set-env-vars SERVER.PORT="8080" \
  --set-env-vars SPRING_PROFILES_ACTIVE="cloud-dev" \
  --set-env-vars TARGET="Native Image - Cloud Run - Postgres 14 - Java 17" \
  --vpc-connector="quote-connector" \
  --region us-central1 \
  --memory 2Gi \
  --service-account cr-quote-service-account \
  --no-allow-unauthenticated

  curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://quotes-native-ieuwkt6jkq-uc.a.run.app

  TOKEN=$(gcloud auth print-identity-token)
  http -A bearer -a $TOKEN https://quotes-native-ieuwkt6jkq-uc.a.run.app

  ---
  curl -v -H 'Content-Type: application/json' -d '{"id":"6","author":"Henry David Thoreau","quote":"Go confidently in the direction of your dreams! Live the life you’ve imagined"}' -X POST 127.0.0.1:8080/quotes

  http POST :8080/quotes author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

  http PUT :8080/quotes/6 author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

  curl -v -X DELETE 127.0.0.1:8080/quotes/6
  http DELETE :8080/quotes/6

  curl -v -X DELETE 127.0.0.1:8080/quotes/6
  http DELETE :8080/quotes/6  


---

  gcloud run deploy bff --image gcr.io/dan-workshop-project-332213/bff-jvm \
  --set-env-vars SERVER.PORT="8080" \
  --set-env-vars QUOTES_URL="https://quotes-ieuwkt6jkq-uc.a.run.app" \
  --set-env-vars REFERENCE_URL="https://reference-ieuwkt6jkq-uc.a.run.app" \
  --region us-central1 \
  --memory 2Gi \
  --allow-unauthenticated

  gcloud run deploy bff-to-native --image gcr.io/dan-workshop-project-332213/bff-jvm \
  --set-env-vars SERVER.PORT="8080" \
  --set-env-vars QUOTES_URL="https://quotes-native-ieuwkt6jkq-uc.a.run.app" \
  --set-env-vars REFERENCE_URL="https://reference-ieuwkt6jkq-uc.a.run.app" \
  --region us-central1 \
  --memory 2Gi \
  --allow-unauthenticated

  curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://bff-ieuwkt6jkq-uc.a.run.app/quotes
  curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://bff-native-ieuwkt6jkq-uc.a.run.app/quotes

http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/quotes
http -A bearer -a $TOKEN https://bff-to-native-ieuwkt6jkq-uc.a.run.app/quotes

gcloud iam service-accounts create cr-bff-service-account \
  --display-name="Reference service Cloud Run Service Account"

gcloud iam service-accounts create cr-reference-service-account \
  --display-name="BFF service Cloud Run Service Account"

gcloud run services add-iam-policy-binding reference \
  --member serviceAccount:cr-bff-service-account@dan-workshop-project-332213.iam.gserviceaccount.com \
  --role roles/run.invoker

gcloud run services add-iam-policy-binding quotes \
  --member serviceAccount:cr-bff-service-account@dan-workshop-project-332213.iam.gserviceaccount.com \
  --role roles/run.invoker

gcloud run services add-iam-policy-binding quotes-native \
  --member serviceAccount:cr-bff-service-account@dan-workshop-project-332213.iam.gserviceaccount.com \
  --role roles/run.invoker

REFERENCE SERVICE
===

  gcloud run deploy reference --image gcr.io/dan-workshop-project-332213/reference-jvm \
  --set-env-vars SERVER.PORT="8080" \
  --set-env-vars DELAY=5 \
  --region us-central1 \
  --memory 2Gi \
  --service-account cr-reference-service-account \
  --no-allow-unauthenticated

   TOKEN=$(gcloud auth print-identity-token)
  http -A bearer -a $TOKEN https://reference-ieuwkt6jkq-uc.a.run.app/metadata
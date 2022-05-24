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
  --vpc-connector="quote-connector" \
  --region us-central1 \
  --memory 2Gi \
  --allow-unauthenticated

  curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://quotes-ieuwkt6jkq-uc.a.run.app

  =======================

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
  --vpc-connector="quote-connector" \
  --region us-central1 \
  --memory 2Gi \
  --allow-unauthenticated
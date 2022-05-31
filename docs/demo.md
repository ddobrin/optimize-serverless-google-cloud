Logs filter:
HealthCheck OR StartupCheck


curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://quotes-ieuwkt6jkq-uc.a.run.app

!!!
TOKEN=$(gcloud auth print-identity-token)
time http -A bearer -a $TOKEN https://quotes-ieuwkt6jkq-uc.a.run.app/quotes
time http -A bearer -a $TOKEN https://quotes-native-ieuwkt6jkq-uc.a.run.app/quotes

TOKEN=$(gcloud auth print-identity-token)
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://reference-ieuwkt6jkq-uc.a.run.app/metadata
time http -A bearer -a $TOKEN https://reference-ieuwkt6jkq-uc.a.run.app/metadata
time http -A bearer -a $TOKEN https://reference-ieuwkt6jkq-uc.a.run.app/actuator


---
TEST CR: !!!
time http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/quotes
time http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/actuator/health
time http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/actuator/startup

time http -A bearer -a $TOKEN https://bff-to-native-ieuwkt6jkq-uc.a.run.app/quotes
time http -A bearer -a $TOKEN https://bff-dont-ieuwkt6jkq-uc.a.run.app/quotes


curl -v -H 'Content-Type: application/json' -d '{"id":"6","author":"Henry David Thoreau","quote":"Go confidently in the direction of your dreams! Live the life you’ve imagined"}' -X POST 127.0.0.1:8080/quotes

---

time http -A bearer -a $TOKEN POST https://bff-ieuwkt6jkq-uc.a.run.app/quotes author="Henry Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

time http -A bearer -a $TOKEN PUT https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6 author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

curl -v -X DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6
time http -A bearer -a $TOKEN DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6

curl -v -X DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6
time http -A bearer -a $TOKEN DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6  






---

curl -v -H 'Content-Type: application/json' -d '{"id":"6","author":"Henry David Thoreau","quote":"Go confidently in the direction of your dreams! Live the life you’ve imagined"}' -X POST 127.0.0.1:8080/quotes

time http POST :8080/quotes author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

time http PUT :8080/quotes/6 author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

curl -v -X DELETE 127.0.0.1:8080/quotes/6
http DELETE :8080/quotes/6

curl -v -X DELETE 127.0.0.1:8080/quotes/6
http DELETE :8080/quotes/6  


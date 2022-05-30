Logs filter:
HealthCheck OR StartupCheck


curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://quotes-ieuwkt6jkq-uc.a.run.app

TOKEN=$(gcloud auth print-identity-token)
http -A bearer -a $TOKEN https://quotes-ieuwkt6jkq-uc.a.run.app

TOKEN=$(gcloud auth print-identity-token)
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" https://reference-ieuwkt6jkq-uc.a.run.app/metadata
http -A bearer -a $TOKEN https://reference-ieuwkt6jkq-uc.a.run.app/metadata
http -A bearer -a $TOKEN https://reference-ieuwkt6jkq-uc.a.run.app/actuator


---
TEST CR:
http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/quotes
http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/actuator/health
http -A bearer -a $TOKEN https://bff-ieuwkt6jkq-uc.a.run.app/actuator/startup

http -A bearer -a $TOKEN https://bff-to-native-ieuwkt6jkq-uc.a.run.app/quotes

curl -v -H 'Content-Type: application/json' -d '{"id":"6","author":"Henry David Thoreau","quote":"Go confidently in the direction of your dreams! Live the life you’ve imagined"}' -X POST 127.0.0.1:8080/quotes

---

http -A bearer -a $TOKEN POST https://bff-ieuwkt6jkq-uc.a.run.app/quotes author="Henry Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

http -A bearer -a $TOKEN PUT https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6 author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

curl -v -X DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6
http -A bearer -a $TOKEN DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6

curl -v -X DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6
http -A bearer -a $TOKEN DELETE https://bff-ieuwkt6jkq-uc.a.run.app/quotes/6  






---

curl -v -H 'Content-Type: application/json' -d '{"id":"6","author":"Henry David Thoreau","quote":"Go confidently in the direction of your dreams! Live the life you’ve imagined"}' -X POST 127.0.0.1:8080/quotes

http POST :8080/quotes author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

http PUT :8080/quotes/6 author="Henry David Thoreau" quote="Go confidently in the direction of your dreams! Live the life you’ve imagined" id="6"

curl -v -X DELETE 127.0.0.1:8080/quotes/6
http DELETE :8080/quotes/6

curl -v -X DELETE 127.0.0.1:8080/quotes/6
http DELETE :8080/quotes/6  


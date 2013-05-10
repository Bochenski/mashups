import urllib
import json

response = urllib.urlopen("http://search.twitter.com/search.json?q=microsoft")
response = json.load(response)
results = response["results"]

for i in range(10):
    print results[i]["text"]

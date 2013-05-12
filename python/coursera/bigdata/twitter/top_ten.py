import sys
import json


def main():
    tweet_file = open(sys.argv[1])
    scores = {}
    tags = {}

    #ok so we have a dictionary of lowercase words
    for line in tweet_file:
        tweet = json.loads(line)
        if 'text' in tweet:
            entities = tweet['entities']
            for tag in entities['hashtags']:
                hashtag = tag['text']
                if hashtag in tags:
                    tags[hashtag] += 1
                else:
                    tags[hashtag] = 1
    top10 = sorted(tags, key=tags.get, reverse=True)[:10]
    for tag in top10:
        print tag + " " + str(tags[tag])

if __name__ == '__main__':
    main()

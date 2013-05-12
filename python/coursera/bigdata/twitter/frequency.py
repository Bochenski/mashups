import sys
import json
from decimal import Decimal


def main():
    tweet_file = open(sys.argv[1])
    scores = {}
    terms = {}

    #ok so we have a dictionary of lowercase words
    i = 0
    for line in tweet_file:
        tweet = json.loads(line)
        if 'text' in tweet:
            message = tweet['text']

            for word in message.lower().split():
                i += 1
                # have we already got a score for this word?
                if word in terms:
                    terms[word] += 1
                else:
                    terms[word] = 1
    for term in terms:
        print term + " " + str(Decimal(terms[term]) / Decimal(i))

if __name__ == '__main__':
    main()

import sys
import json


def lines(fp):
    print str(len(fp.readlines()))


def main():
    sent_file = open(sys.argv[1])
    tweet_file = open(sys.argv[2])
    scores = {}
    for line in sent_file:
        term, score = line.split("\t")
        scores[term] = int(score)

    #ok so we have a dictionary of lowercase words
    for line in tweet_file:
        tweet = json.loads(line)
        if 'text' in tweet:
            message = tweet['text']
            score = 0
            for word in message.lower().split():
                if word in scores:
                    score += scores[word]
            print score
        else:
            print 0

if __name__ == '__main__':
    main()

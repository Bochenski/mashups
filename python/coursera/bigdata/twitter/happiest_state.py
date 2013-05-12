import sys
import json


def main():
    sent_file = open(sys.argv[1])
    tweet_file = open(sys.argv[2])
    scores = {}
    stateScores = {}
    for line in sent_file:
        term, score = line.split("\t")
        scores[term] = int(score)

    #ok so we have a dictionary of lowercase words
    for line in tweet_file:
        tweet = json.loads(line)
        if 'place' in tweet:
            place = tweet['place']
            if place:
                if place['country_code'] == 'US':
                    if 'full_name' in place:
                        state = place['full_name'].split(',')[1].strip()
                        if not state in stateScores:
                            stateScores[state] = 0
                        if 'text' in tweet:
                            message = tweet['text']
                            score = 0
                            for word in message.lower().split():
                                if word in scores:
                                    score += scores[word]
                            stateScores[state] += score
    happiest = sorted(stateScores, key=stateScores.get, reverse=True)[:1]
    print happiest[0]

if __name__ == '__main__':
    main()

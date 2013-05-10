import sys
import json


def lines(fp):
    print str(len(fp.readlines()))


def main():
    sent_file = open(sys.argv[1])
    tweet_file = open(sys.argv[2])
    scores = {}
    terms = {}
    for line in sent_file:
        term, score = line.split("\t")
        scores[term] = int(score)

    #ok so we have a dictionary of lowercase words
    i = 0
    for line in tweet_file:
        i += 1
        if i > 1000:
            print 'bailing'
            break
        tweet = json.loads(line)
        if 'text' in tweet:
            message = tweet['text']
            score = 0
            for word in message.lower().split():
                if word in scores:
                    score += scores[word]
            for word in message.lower().split():
                if not word in scores:
                    # have we already got a score for this word?
                    if word in terms:
                        terms[word]['score'] += score
                        terms[word]['occurances'] += 1
                    else:
                        terms[word] = {'occurances': 1, 'score': score}
    for term in terms:
        print term + " " + str(terms[term]['score'] / terms[term]['occurances'])

if __name__ == '__main__':
    main()

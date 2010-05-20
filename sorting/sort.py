
def insertion(seq):
    for i in range(0, len(seq)-1):
        j = i + 1
        while j <> 0 and seq[j] < seq[j-1]:
            seq[j], seq[j-1] = seq[j-1], seq[j]
            j -= 1
    return seq



def insertion_sort(seq):
    for i in range(0, len(seq)-1):
        j = i + 1
        while j <> 0 and seq[j] < seq[j-1]:
            seq[j], seq[j-1] = seq[j-1], seq[j]
            j -= 1
    return seq

def merge_sort(seq):
    if len(seq) in (0,1):
        return seq

    m = len(seq) / 2

    nl, left = 0, merge_sort(seq[:m])
    nr, right = 0, merge_sort(seq[m:])
    
    result = []
    while nl < len(left) and nr < len(right):
        if left[nl] < right[nr]:
            result.append(left[nl])
            nl += 1
        else:
            result.append(right[nr])
            nr += 1

    if nl == len(left):
        result += right[nr:]

    if nr == len(right):
        result += left[nl:]

    return result


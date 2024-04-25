#Să se determine al k-lea cel mai mare element al unui șir
#de numere cu n elemente (k < n). De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7.


# complexitatea timp: O(n log n)
# complexitatea spatiu: O(1)

#sortam desc
#dupa accesam k-1 si vedem care e
def kth_smallest(array, k):
    array.sort(reverse=True)
    return array[k - 1]


def test():
    assert (kth_smallest([7, 4, 6, 3, 9, 1], 2) == 7)
    assert (kth_smallest([1, 2, 3, 9, 8, 4, 5], 3) == 5)
    assert (kth_smallest([1, 1, 5, 4, 3, 5, 5, 7], 1) == 7)


if __name__ == '__main__':
    test()
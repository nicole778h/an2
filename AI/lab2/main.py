#Se da un fisier care contine un text (format din mai multe propozitii) in limba romana - a se vedea fisierul ”data/texts.txt”. Se cere sa se determine si sa se vizualizeze:

#numarul de propozitii din text;
#numarul de cuvinte din text
#numarul de cuvinte diferite din text
#cel mai scurt si cel mai lung cuvant (cuvinte)
#textul fara diacritice
#sinonimele celui mai lung cuvant din text

import string

import nltk
from googletrans import Translator
from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.corpus import wordnet
import unidecode
import requests
from nltk.corpus import wordnet
import translators as ts
import nltk
nltk.download('wordnet')


#calculeaza nr propozitilor din text
def nr_propozitii(text):
    propozitii = sent_tokenize(text, language='english')
    return len(propozitii)

#calculeaza nr cuv din text eliminand punctuatia
def nr_cuvinte(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte = [word for word in cuvinte if word not in string.punctuation]
    return len(cuvinte)

#calculeaza nr cuvinte diferite din text eliminand punctuatie
def nr_cuvinte_diferite(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte = [word for word in cuvinte if word not in string.punctuation]
    cuvinte_diferite = set(cuvinte)
    return len(cuvinte_diferite)


#gaseste cel mai scurt si cel mai lung cuvantg din text

def cel_mai_scurt_si_cel_mai_lung_cuvant(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte = [word for word in cuvinte if word not in string.punctuation]
    cuvinte.sort(key=len)
    cel_mai_scurt = cuvinte[0]
    cel_mai_lung = cuvinte[-1]
    return cel_mai_scurt, cel_mai_lung

#elimina diactritice

def text_fara_diacritice(text):
    return unidecode.unidecode(text)
#gaseste cel mai lung cuv din text apoi ii cauta sinonime
def sinonime_cel_mai_lung_cuvant(text):
    cuvinte = word_tokenize(text, language='english')
    cuvinte.sort(key=len)
    cel_mai_lung = cuvinte[-1]
    sinonime = []
    translator = Translator()
    cuvant_tradus = translator.translate(cel_mai_lung, src='ro', dest='en')
    for syn in wordnet.synsets(cuvant_tradus, lang='eng'):
        for lemma in syn.lemmas(lang='eng'):
            sinonime.append(lemma.name())

        # Returnați un set de sinonime pentru cel mai lung cuvânt
    return set(sinonime)

def sinonimeee(text):
    short,long = cel_mai_scurt_si_cel_mai_lung_cuvant(text)
    trans = ts.translate_text(long)
    syn = set()
    for sinonim in wordnet.synsets(trans):
        for lemma in sinonim.lemmas():
            syn.add(lemma.name())

    return list(syn)


# Apelul funcției pentru a obține sinonimele cuvântului cel mai lung

def main():
    with open("C:\\Users\\Galdeanu\\Desktop\\facultate\\ai_lab2_3\\texts.txt", 'r', encoding='utf-8') as file:
        text = file.read()

    # Afișăm rezultatele
    print("a. Nr prop din text:", nr_propozitii(text))
    print("b. Nr cuv din text:", nr_cuvinte(text))
    print("c. Nr  cuv dif din text:", nr_cuvinte_diferite(text))
    cel_mai_scurt, cel_mai_lung = cel_mai_scurt_si_cel_mai_lung_cuvant(text)
    print("d. Cel mai scurt cuv:", cel_mai_scurt)
    print("   Cel mai lung cuv:", cel_mai_lung)
    print("e. Textul fără diacritice:\n", text_fara_diacritice(text))
    sinonime = sinonimeee(text)
    print("Sinonimele celui mai lung cuvant sunt: " + str(sinonime))



main()
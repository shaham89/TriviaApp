import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from Subjects import *

cred = credentials.Certificate("servicesKey.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

QUESTION_COLLECTION_TEXT = u'subject_questions'

CAPITALS_TEXT = u'capitals'

SUBJECT_LIST = [Capitals]

def init_subjects():
    for subject in SUBJECT_LIST:
        print(subject().get_name())
        subject().init_questions(db.collection('qu'))


init_subjects()

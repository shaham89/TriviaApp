import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from Subjects import *


cred = credentials.Certificate("servicesKey.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

QUESTION_COLLECTION_TEXT = u'subjects_questions'

CAPITALS_TEXT = u'capitals'

SUBJECT_LIST = [Capitals]

def init_subjects_questions():
    for subject in SUBJECT_LIST:
        subject_name = subject().get_name()
        print(subject_name)
        collection_path = QUESTION_COLLECTION_TEXT + "/" + subject_name + "_subject/" + subject_name + "_questions"
        print(collection_path)
        subject().init_questions(db.collection(collection_path))



init_subjects_questions()

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from Subjects import *
from Capitals import Capitals
from Astronomy import Astronomy
from Uploader import *

cred = credentials.Certificate("servicesKey.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

QUESTION_COLLECTION_TEXT = u'subjects_questions'

CAPITALS_TEXT = u'capitals'

#SUBJECT_LIST = [Capitals]
# SUBJECT_LIST = [Astronomy]
#
# def init_subjects_questions():
#     for subject in SUBJECT_LIST:
#
#         subject().init_questions(db)

#two 50 easy and one 33 medium
# upload_pkl("general_knowledge", db)

upload_pkl("history", db)

#init_subjects_questions()

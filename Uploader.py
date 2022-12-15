from abc import ABC, abstractmethod
import ast
import random
from Subjects import *
import pickle
import os


def get_questions(file_name):
    # used question are: hard, 40; easy, 43; medium, 59
    questions_list = []
    if os.path.exists(file_name):
        with open(file_name, "rb") as f:
            questions_list = pickle.load(f)

    questions = []
    for question in questions_list:
        questions.append(Question(question[Question.QUESTION_TEXT], question[Question.OPTIONS_TEXT],
                                  question[Question.TRUE_ANSWER_TEXT]))

    return questions

def upload_pkl(subject_name, db):
    FILE_NAME = "Data\\" + subject_name + ".pkl"

    questions = get_questions(FILE_NAME)

    i = 0

    print(subject_name)
    document_name = subject_name + "_subject"
    collection_path = Subject.QUESTION_COLLECTION_TEXT + "/" + subject_name + "_subject/" + subject_name + "_questions"
    print(collection_path)

    for q in questions:
        db.collection(collection_path).add({Question.QUESTION_TEXT: q.get_question(),
                                            Question.OPTIONS_TEXT: q.get_options(),
                                            Question.TRUE_ANSWER_TEXT: q.get_answer(),
                                            Question.INDEX_TEXT: i})
        i += 1
    db.collection(QUESTION_COLLECTION_TEXT).document(document_name).set({Question.LENGTH_TEXT: i})


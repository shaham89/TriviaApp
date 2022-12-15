import pickle
from Subjects import *
from Astronomy import Astronomy
import os

def extract_questions(trivia_dict):
    trivia_dict = trivia_dict['trivia']['questions']

    questions_list = []
    for question in trivia_dict:
        current_question = {Question.QUESTION_TEXT: question['question'], Question.OPTIONS_TEXT: []}
        for ans in question['answers']:
            current_question[Question.OPTIONS_TEXT].append(ans['answer'])
            if ans['correct']:
                current_question[Question.TRUE_ANSWER_TEXT] = ans['answer']

        questions_list.append(current_question)

    return questions_list

def append_questions(trivia_dict, file_path):
    new_questions = extract_questions(trivia_dict)

    file_questions = []
    if os.path.exists(Astronomy.FILE_NAME):
        with open(Astronomy.FILE_NAME, "rb") as f:
            file_questions = pickle.load(f)

    new_questions = new_questions + file_questions

    with open(file_path, "wb") as f:
        pickle.dump(new_questions, f)


window
window = window.replace('null', 'None')
window = window.replace('false', 'False')
window = window.replace('true', 'True')
window = eval(window)

print(append_questions(window, 'Data\\general_knowledge.pkl'))
#print()

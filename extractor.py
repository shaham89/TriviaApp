import pickle
from Subjects import *
from Astronomy import Astronomy

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
    with open(Astronomy.FILE_NAME, "rb") as f:
        file_questions = pickle.load(f)

    new_questions = new_questions + file_questions

    with open(file_path, "wb") as f:
        pickle.dump(new_questions, f)


window = {"id": 224297, "user_id": None, "trivia_id": 2341, "created_at": "2022-12-12T19:18:02.000000Z",
                       "updated_at": "2022-12-12T19:18:02.000000Z", "hash_id": "A6bOKYgmzqZywdJk9ZNBLjJRnaxp2v89",
                       "trivia": {"id": 2341, "user_id": None, "title": "Astronomy, Space \u0026 Planets - Easy",
                                  "meta_title": None, "description": None, "difficulty": 1, "is_public": True,
                                  "is_featured": True, "created_at": "2022-04-27T08:02:28.000000Z",
                                  "updated_at": "2022-05-06T09:13:47.000000Z", "questions": [
                               {"id": 35387, "trivia_id": 2341,
                                "question": "Which of these would be the greatest distance apart?", "order": None,
                                "accuracy": None, "verified": None, "created_at": "2022-04-27T08:02:28.000000Z",
                                "updated_at": "2022-04-27T08:02:28.000000Z",
                                "answers": [{"id": 141536, "question_id": 35387, "answer": "2 Galaxies", "correct": True},
                                            {"id": 141541, "question_id": 35387, "answer": "2 Planets", "correct": False},
                                            {"id": 141548, "question_id": 35387, "answer": "2 Stars", "correct": False},
                                            {"id": 141556, "question_id": 35387, "answer": "2 Moons", "correct": False}]},
                               {"id": 35397, "trivia_id": 2341, "question": "In Roman mythology, what was Mercury? ",
                                "order": None, "accuracy": None, "verified": None,
                                "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                                "answers": [
                                    {"id": 141575, "question_id": 35397, "answer": "The messenger god", "correct": True},
                                    {"id": 141583, "question_id": 35397, "answer": "God of war", "correct": False},
                                    {"id": 141590, "question_id": 35397, "answer": "God of peace", "correct": False},
                                    {"id": 141597, "question_id": 35397, "answer": "God of beauty", "correct": False}]},
                               {"id": 35405, "trivia_id": 2341,
                                "question": "What is the title of the BBC TV factual space series that\u0027s been running since 1957?",
                                "order": None, "accuracy": None, "verified": None,
                                "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                                "answers": [
                                    {"id": 141613, "question_id": 35405, "answer": "The Sky At Night", "correct": True},
                                    {"id": 141620, "question_id": 35405, "answer": "The Man In The Moon", "correct": False},
                                    {"id": 141624, "question_id": 35405, "answer": "Stars In Their Eyes", "correct": False},
                                    {"id": 141636, "question_id": 35405, "answer": "Space Jam", "correct": False}]},
                               {"id": 35416, "trivia_id": 2341,
                                "question": "How long does it take for a launch shuttle to clear the pad tower?",
                                "order": None, "accuracy": None, "verified": None,
                                "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                                "answers": [{"id": 141655, "question_id": 35416, "answer": "7 seconds", "correct": True},
                                            {"id": 141664, "question_id": 35416, "answer": "10 minutes", "correct": False},
                                            {"id": 141668, "question_id": 35416, "answer": "3 days", "correct": False},
                                            {"id": 141680, "question_id": 35416, "answer": "3 weeks", "correct": False}]},
                               {"id": 35428, "trivia_id": 2341,
                                "question": "Which constellation has a Latin name meaning little bear?", "order": None,
                                "accuracy": None, "verified": None, "created_at": "2022-04-27T08:02:28.000000Z",
                                "updated_at": "2022-04-27T08:02:28.000000Z",
                                "answers": [{"id": 141696, "question_id": 35428, "answer": "Ursa Minor", "correct": True},
                                            {"id": 141705, "question_id": 35428, "answer": "Draco", "correct": False},
                                            {"id": 141712, "question_id": 35428, "answer": "Cassiopeia", "correct": False},
                                            {"id": 141718, "question_id": 35428, "answer": "Orion", "correct": False}]},
                               {"id": 35437, "trivia_id": 2341, "question": "Which of these options is a type of star?",
                                "order": None, "accuracy": None, "verified": None,
                                "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                                "answers": [{"id": 141728, "question_id": 35437, "answer": "Red dwarf", "correct": True},
                                            {"id": 141736, "question_id": 35437, "answer": "Lightning", "correct": False},
                                            {"id": 141744, "question_id": 35437, "answer": "Draco", "correct": False},
                                            {"id": 141755, "question_id": 35437, "answer": "Snow Storm",
                                             "correct": False}]}]}, "answers": []}

print(append_questions(window, 'Data\\astronomy.pkl'))
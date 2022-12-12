from abc import ABC, abstractmethod
import ast
import random
from Subjects import *

class Astronomy(Subject):
    NAME = 'astronomy'

    def get_name(self):
        return self.NAME

    @staticmethod
    def get_questions():

        main_question = 'What is the capital of '

    window.GAME = {"id": 224297, "user_id": null, "trivia_id": 2341, "created_at": "2022-12-12T19:18:02.000000Z",
                   "updated_at": "2022-12-12T19:18:02.000000Z", "hash_id": "A6bOKYgmzqZywdJk9ZNBLjJRnaxp2v89",
                   "trivia": {"id": 2341, "user_id": null, "title": "Astronomy, Space \u0026 Planets - Easy",
                              "meta_title": null, "description": null, "difficulty": 1, "is_public": true,
                              "is_featured": true, "created_at": "2022-04-27T08:02:28.000000Z",
                              "updated_at": "2022-05-06T09:13:47.000000Z", "questions": [
                           {"id": 35387, "trivia_id": 2341,
                            "question": "Which of these would be the greatest distance apart?", "order": null,
                            "accuracy": null, "verified": null, "created_at": "2022-04-27T08:02:28.000000Z",
                            "updated_at": "2022-04-27T08:02:28.000000Z",
                            "answers": [{"id": 141536, "question_id": 35387, "answer": "2 Galaxies", "correct": true},
                                        {"id": 141541, "question_id": 35387, "answer": "2 Planets", "correct": false},
                                        {"id": 141548, "question_id": 35387, "answer": "2 Stars", "correct": false},
                                        {"id": 141556, "question_id": 35387, "answer": "2 Moons", "correct": false}]},
                           {"id": 35397, "trivia_id": 2341, "question": "In Roman mythology, what was Mercury? ",
                            "order": null, "accuracy": null, "verified": null,
                            "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                            "answers": [
                                {"id": 141575, "question_id": 35397, "answer": "The messenger god", "correct": true},
                                {"id": 141583, "question_id": 35397, "answer": "God of war", "correct": false},
                                {"id": 141590, "question_id": 35397, "answer": "God of peace", "correct": false},
                                {"id": 141597, "question_id": 35397, "answer": "God of beauty", "correct": false}]},
                           {"id": 35405, "trivia_id": 2341,
                            "question": "What is the title of the BBC TV factual space series that\u0027s been running since 1957?",
                            "order": null, "accuracy": null, "verified": null,
                            "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                            "answers": [
                                {"id": 141613, "question_id": 35405, "answer": "The Sky At Night", "correct": true},
                                {"id": 141620, "question_id": 35405, "answer": "The Man In The Moon", "correct": false},
                                {"id": 141624, "question_id": 35405, "answer": "Stars In Their Eyes", "correct": false},
                                {"id": 141636, "question_id": 35405, "answer": "Space Jam", "correct": false}]},
                           {"id": 35416, "trivia_id": 2341,
                            "question": "How long does it take for a launch shuttle to clear the pad tower?",
                            "order": null, "accuracy": null, "verified": null,
                            "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                            "answers": [{"id": 141655, "question_id": 35416, "answer": "7 seconds", "correct": true},
                                        {"id": 141664, "question_id": 35416, "answer": "10 minutes", "correct": false},
                                        {"id": 141668, "question_id": 35416, "answer": "3 days", "correct": false},
                                        {"id": 141680, "question_id": 35416, "answer": "3 weeks", "correct": false}]},
                           {"id": 35428, "trivia_id": 2341,
                            "question": "Which constellation has a Latin name meaning little bear?", "order": null,
                            "accuracy": null, "verified": null, "created_at": "2022-04-27T08:02:28.000000Z",
                            "updated_at": "2022-04-27T08:02:28.000000Z",
                            "answers": [{"id": 141696, "question_id": 35428, "answer": "Ursa Minor", "correct": true},
                                        {"id": 141705, "question_id": 35428, "answer": "Draco", "correct": false},
                                        {"id": 141712, "question_id": 35428, "answer": "Cassiopeia", "correct": false},
                                        {"id": 141718, "question_id": 35428, "answer": "Orion", "correct": false}]},
                           {"id": 35437, "trivia_id": 2341, "question": "Which of these options is a type of star?",
                            "order": null, "accuracy": null, "verified": null,
                            "created_at": "2022-04-27T08:02:28.000000Z", "updated_at": "2022-04-27T08:02:28.000000Z",
                            "answers": [{"id": 141728, "question_id": 35437, "answer": "Red dwarf", "correct": true},
                                        {"id": 141736, "question_id": 35437, "answer": "Lightning", "correct": false},
                                        {"id": 141744, "question_id": 35437, "answer": "Draco", "correct": false},
                                        {"id": 141755, "question_id": 35437, "answer": "Snow Storm",
                                         "correct": false}]}]}, "answers": []};

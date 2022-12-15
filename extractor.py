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
    if os.path.exists(file_path):
        with open(file_path, "rb") as f:
            file_questions = pickle.load(f)

    new_questions = new_questions + file_questions

    with open(file_path, "wb") as f:
        pickle.dump(new_questions, f)

window = """{"id":227486,"user_id":null,"trivia_id":4886,"created_at":"2022-12-15T11:27:00.000000Z","updated_at":"2022-12-15T11:27:00.000000Z","hash_id":"9ybD7e0Wm6EawPg7AR9PrKjgO8XM2Yq5","trivia":{"id":4886,"user_id":null,"title":"History General - Medium","meta_title":null,"description":null,"difficulty":2,"is_public":true,"is_featured":true,"created_at":"2022-04-27T08:06:46.000000Z","updated_at":"2022-05-06T09:14:20.000000Z","questions":[{"id":74009,"trivia_id":4886,"question":"Who wrote the 17th century novel \u0027Don Quixote\u0027?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296015,"question_id":74009,"answer":"Cervantes","correct":true},{"id":296024,"question_id":74009,"answer":"Shakespeare","correct":false},{"id":296032,"question_id":74009,"answer":"Bacon","correct":false},{"id":296034,"question_id":74009,"answer":"Washington","correct":false}]},{"id":73890,"trivia_id":4886,"question":"Which Soviet leader supported North Vietnam through the civil war?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295546,"question_id":73890,"answer":"Brezhnev","correct":true},{"id":295555,"question_id":73890,"answer":"Gorbachev","correct":false},{"id":295562,"question_id":73890,"answer":"Putin","correct":false},{"id":295566,"question_id":73890,"answer":"Lenin","correct":false}]},{"id":73901,"trivia_id":4886,"question":"Which world figure was assassinated by Nathuram Godse in 1948?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295588,"question_id":73901,"answer":"Gandhi","correct":true},{"id":295596,"question_id":73901,"answer":"Kennedy","correct":false},{"id":295602,"question_id":73901,"answer":"Lincoln","correct":false},{"id":295608,"question_id":73901,"answer":"Mitterrand","correct":false}]},{"id":73911,"trivia_id":4886,"question":"Emmerson Mnangagwa became President of which African country in 2017?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295625,"question_id":73911,"answer":"Zimbabwe","correct":true},{"id":295634,"question_id":73911,"answer":"Ghana","correct":false},{"id":295640,"question_id":73911,"answer":"South Africa","correct":false},{"id":295648,"question_id":73911,"answer":"Cameroon","correct":false}]},{"id":73921,"trivia_id":4886,"question":"What was Lenin\u0027s job before the Russian Revolution?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295663,"question_id":73921,"answer":"Lawyer","correct":true},{"id":295669,"question_id":73921,"answer":"Doctor","correct":false},{"id":295678,"question_id":73921,"answer":"Accountant","correct":false},{"id":295685,"question_id":73921,"answer":"Teacher","correct":false}]},{"id":73929,"trivia_id":4886,"question":"A 1608 fire destroyed the Jamestown colony in which US state?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295700,"question_id":73929,"answer":"Virginia","correct":true},{"id":295708,"question_id":73929,"answer":"Massachusetts","correct":false},{"id":295716,"question_id":73929,"answer":"Connecticut","correct":false},{"id":295724,"question_id":73929,"answer":"Georgia","correct":false}]},{"id":73941,"trivia_id":4886,"question":"Alexander the Great grew up in which European city?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295745,"question_id":73941,"answer":"Athens","correct":true},{"id":295752,"question_id":73941,"answer":"Berlin","correct":false},{"id":295759,"question_id":73941,"answer":"Madrid","correct":false},{"id":295765,"question_id":73941,"answer":"Rome","correct":false}]},{"id":73950,"trivia_id":4886,"question":"The Indian name \u0027Bapu\u0027 refers to which leader?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295781,"question_id":73950,"answer":"Gandhi","correct":true},{"id":295789,"question_id":73950,"answer":"Nelson Mandela","correct":false},{"id":295796,"question_id":73950,"answer":"Nehru","correct":false},{"id":295801,"question_id":73950,"answer":"Golda Meir","correct":false}]},{"id":73959,"trivia_id":4886,"question":"Britain, Ireland and which other country joined the EEC in 1973?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295819,"question_id":73959,"answer":"Denmark","correct":true},{"id":295830,"question_id":73959,"answer":"Norway","correct":false},{"id":295835,"question_id":73959,"answer":"Netherlands","correct":false},{"id":295842,"question_id":73959,"answer":"Spain","correct":false}]},{"id":73969,"trivia_id":4886,"question":"What was the name of Henry VIII\u0027s older brother, first married to Catherine of Aragon?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295860,"question_id":73969,"answer":"Arthur","correct":true},{"id":295867,"question_id":73969,"answer":"John","correct":false},{"id":295873,"question_id":73969,"answer":"Philip","correct":false},{"id":295881,"question_id":73969,"answer":"Frederick","correct":false}]},{"id":73980,"trivia_id":4886,"question":"Captain Meriwether Lewis was the private secretary of which President?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295897,"question_id":73980,"answer":"Jefferson","correct":true},{"id":295907,"question_id":73980,"answer":"Washington","correct":false},{"id":295912,"question_id":73980,"answer":"Lincoln","correct":false},{"id":295921,"question_id":73980,"answer":"Nixon","correct":false}]},{"id":73989,"trivia_id":4886,"question":"In 1994, troops from which country withdrew from the Gaza Strip?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295941,"question_id":73989,"answer":"Israel","correct":true},{"id":295947,"question_id":73989,"answer":"Malaysia","correct":false},{"id":295955,"question_id":73989,"answer":"Russia","correct":false},{"id":295960,"question_id":73989,"answer":"China","correct":false}]},{"id":74000,"trivia_id":4886,"question":"Which mainland US state has the highest percentage of native Americans?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":295981,"question_id":74000,"answer":"New Mexico","correct":true},{"id":295988,"question_id":74000,"answer":"California","correct":false},{"id":295996,"question_id":74000,"answer":"Oregon","correct":false},{"id":296000,"question_id":74000,"answer":"Ohio","correct":false}]},{"id":73882,"trivia_id":4886,"question":"The city of Riga, founded in 1201, is in which modern country?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295511,"question_id":73882,"answer":"Latvia","correct":true},{"id":295519,"question_id":73882,"answer":"Lithuania","correct":false},{"id":295525,"question_id":73882,"answer":"Russia","correct":false},{"id":295531,"question_id":73882,"answer":"Germany","correct":false}]},{"id":74018,"trivia_id":4886,"question":"Vaclav Havel was the first President of which former Eastern Bloc state?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296049,"question_id":74018,"answer":"Czech Republic","correct":true},{"id":296057,"question_id":74018,"answer":"Hungary","correct":false},{"id":296065,"question_id":74018,"answer":"Bulgaria","correct":false},{"id":296070,"question_id":74018,"answer":"Romania","correct":false}]},{"id":74027,"trivia_id":4886,"question":"Henry VIII was born in Greenwich Palace in which British city?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296085,"question_id":74027,"answer":"London","correct":true},{"id":296093,"question_id":74027,"answer":"Oxford","correct":false},{"id":296100,"question_id":74027,"answer":"Bristol","correct":false},{"id":296103,"question_id":74027,"answer":"Cambridge","correct":false}]},{"id":74033,"trivia_id":4886,"question":"Supersonic aircraft Concorde was a joint venture between the UK and which other country?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296110,"question_id":74033,"answer":"France","correct":true},{"id":296118,"question_id":74033,"answer":"Germany","correct":false},{"id":296127,"question_id":74033,"answer":"Italy","correct":false},{"id":296131,"question_id":74033,"answer":"Spain","correct":false}]},{"id":74039,"trivia_id":4886,"question":"Who was the last leader of the Soviet Union before it dissolved?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296140,"question_id":74039,"answer":"Gorbachev","correct":true},{"id":296143,"question_id":74039,"answer":"Andropov","correct":false},{"id":296148,"question_id":74039,"answer":"Chernenko","correct":false},{"id":296156,"question_id":74039,"answer":"Stalin","correct":false}]},{"id":74045,"trivia_id":4886,"question":"FedEx was founded in which US city, chosen for its central location?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296167,"question_id":74045,"answer":"Memphis","correct":true},{"id":296173,"question_id":74045,"answer":"Dallas","correct":false},{"id":296179,"question_id":74045,"answer":"Minneapolis","correct":false},{"id":296186,"question_id":74045,"answer":"Chicago","correct":false}]},{"id":74052,"trivia_id":4886,"question":"What was the name for an association of craftsmen in the Middle Ages?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296199,"question_id":74052,"answer":"Guild","correct":true},{"id":296203,"question_id":74052,"answer":"Team","correct":false},{"id":296208,"question_id":74052,"answer":"Union","correct":false},{"id":296215,"question_id":74052,"answer":"Co-Operative","correct":false}]},{"id":74060,"trivia_id":4886,"question":"Edwin Hubble was an American scientist in which field?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:51.000000Z","updated_at":"2022-04-27T08:06:51.000000Z","answers":[{"id":296229,"question_id":74060,"answer":"Astronomy","correct":true},{"id":296234,"question_id":74060,"answer":"Agriculture","correct":false},{"id":296237,"question_id":74060,"answer":"Music","correct":false},{"id":296244,"question_id":74060,"answer":"Ancient History","correct":false}]},{"id":74068,"trivia_id":4886,"question":"Who was the leader of North Vietnam during the war?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:52.000000Z","updated_at":"2022-04-27T08:06:52.000000Z","answers":[{"id":296263,"question_id":74068,"answer":"Ho Chi Minh","correct":true},{"id":296267,"question_id":74068,"answer":"Indira Gandhi","correct":false},{"id":296273,"question_id":74068,"answer":"Pol Pot","correct":false},{"id":296279,"question_id":74068,"answer":"Emperor Akihito","correct":false}]},{"id":74076,"trivia_id":4886,"question":"Who was the first President to have a star on the Hollywood Walk of Fame?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:52.000000Z","updated_at":"2022-04-27T08:06:52.000000Z","answers":[{"id":296299,"question_id":74076,"answer":"Ronald Reagan","correct":true},{"id":296304,"question_id":74076,"answer":"Jimmy Carter","correct":false},{"id":296310,"question_id":74076,"answer":"Richard Nixon","correct":false},{"id":296317,"question_id":74076,"answer":"Harry S Truman","correct":false}]},{"id":74086,"trivia_id":4886,"question":"Thurgood Marshall was a prominent African-American in which field?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:52.000000Z","updated_at":"2022-04-27T08:06:52.000000Z","answers":[{"id":296329,"question_id":74086,"answer":"Law","correct":true},{"id":296335,"question_id":74086,"answer":"Military","correct":false},{"id":296341,"question_id":74086,"answer":"Medicine","correct":false},{"id":296347,"question_id":74086,"answer":"Exploration","correct":false}]},{"id":74093,"trivia_id":4886,"question":"Which system of government has a king as the only source of power?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:52.000000Z","updated_at":"2022-04-27T08:06:52.000000Z","answers":[{"id":296358,"question_id":74093,"answer":"Absolute Monarchy","correct":true},{"id":296364,"question_id":74093,"answer":"Democracy","correct":false},{"id":296371,"question_id":74093,"answer":"European Republic","correct":false},{"id":296376,"question_id":74093,"answer":"New Anarchy","correct":false}]},{"id":73774,"trivia_id":4886,"question":"Archbishop Makarios was elected first President of which country?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:48.000000Z","updated_at":"2022-04-27T08:06:48.000000Z","answers":[{"id":295085,"question_id":73774,"answer":"Cyprus","correct":true},{"id":295092,"question_id":73774,"answer":"Malta","correct":false},{"id":295096,"question_id":73774,"answer":"Madagascar","correct":false},{"id":295103,"question_id":73774,"answer":"Jamaica","correct":false}]},{"id":73658,"trivia_id":4886,"question":"What was the name of the US\u0027s first Space Shuttle?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294615,"question_id":73658,"answer":"Columbia","correct":true},{"id":294623,"question_id":73658,"answer":"Discovery","correct":false},{"id":294636,"question_id":73658,"answer":"Endeavour","correct":false},{"id":294644,"question_id":73658,"answer":"Adventure","correct":false}]},{"id":73669,"trivia_id":4886,"question":"General Noriega was deposed as leader of which central American country in 1989?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294656,"question_id":73669,"answer":"Panama","correct":true},{"id":294662,"question_id":73669,"answer":"Guatemala","correct":false},{"id":294674,"question_id":73669,"answer":"Mexico","correct":false},{"id":294681,"question_id":73669,"answer":"Brazil","correct":false}]},{"id":73677,"trivia_id":4886,"question":"The Al-Aqsa Mosque was built in the 11th century in which Middle Eastern city?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294697,"question_id":73677,"answer":"Jerusalem","correct":true},{"id":294703,"question_id":73677,"answer":"Istanbul","correct":false},{"id":294712,"question_id":73677,"answer":"Beirut","correct":false},{"id":294720,"question_id":73677,"answer":"Damascus","correct":false}]},{"id":73687,"trivia_id":4886,"question":"Abraham Lincoln was appointed to represent which state at the age of 25?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294732,"question_id":73687,"answer":"Illinois","correct":true},{"id":294739,"question_id":73687,"answer":"Florida","correct":false},{"id":294751,"question_id":73687,"answer":"Texas","correct":false},{"id":294757,"question_id":73687,"answer":"Rhode Island","correct":false}]},{"id":73697,"trivia_id":4886,"question":"Roman consuls were elected to serve for a period of how long?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294772,"question_id":73697,"answer":"Year","correct":true},{"id":294777,"question_id":73697,"answer":"Month","correct":false},{"id":294786,"question_id":73697,"answer":"Decade","correct":false},{"id":294794,"question_id":73697,"answer":"Week","correct":false}]},{"id":73707,"trivia_id":4886,"question":"Robespierre was a major figure in the revolution of which country?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294811,"question_id":73707,"answer":"France","correct":true},{"id":294816,"question_id":73707,"answer":"Portugal","correct":false},{"id":294821,"question_id":73707,"answer":"Spain","correct":false},{"id":294830,"question_id":73707,"answer":"Belgium","correct":false}]},{"id":73716,"trivia_id":4886,"question":"The Ashanti Empire was a civilization in which modern country?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294848,"question_id":73716,"answer":"Ghana","correct":true},{"id":294853,"question_id":73716,"answer":"Kenya","correct":false},{"id":294860,"question_id":73716,"answer":"Egypt","correct":false},{"id":294867,"question_id":73716,"answer":"Morocco","correct":false}]},{"id":73726,"trivia_id":4886,"question":"Who did Julius Caesar defeat to become Emperor of Rome?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:47.000000Z","updated_at":"2022-04-27T08:06:47.000000Z","answers":[{"id":294891,"question_id":73726,"answer":"Pompey the Great","correct":true},{"id":294896,"question_id":73726,"answer":"Octavius","correct":false},{"id":294901,"question_id":73726,"answer":"Nero","correct":false},{"id":294910,"question_id":73726,"answer":"Caligula","correct":false}]},{"id":73736,"trivia_id":4886,"question":"Which US President was a Rhodes Scholar in Oxford in 1968?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:48.000000Z","updated_at":"2022-04-27T08:06:48.000000Z","answers":[{"id":294933,"question_id":73736,"answer":"Clinton","correct":true},{"id":294937,"question_id":73736,"answer":"Carter","correct":false},{"id":294940,"question_id":73736,"answer":"Reagan","correct":false},{"id":294949,"question_id":73736,"answer":"Kennedy","correct":false}]},{"id":73746,"trivia_id":4886,"question":"Which company made the 747, the world\u0027s first Jumbo Jet?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:48.000000Z","updated_at":"2022-04-27T08:06:48.000000Z","answers":[{"id":294969,"question_id":73746,"answer":"Boeing","correct":true},{"id":294976,"question_id":73746,"answer":"Airbus","correct":false},{"id":294982,"question_id":73746,"answer":"Ford","correct":false},{"id":294989,"question_id":73746,"answer":"Honda","correct":false}]},{"id":73756,"trivia_id":4886,"question":"In which field was Hippocrates famous in Ancient Greece?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:48.000000Z","updated_at":"2022-04-27T08:06:48.000000Z","answers":[{"id":295008,"question_id":73756,"answer":"Medicine","correct":true},{"id":295015,"question_id":73756,"answer":"Law","correct":false},{"id":295023,"question_id":73756,"answer":"Piracy","correct":false},{"id":295030,"question_id":73756,"answer":"Art","correct":false}]},{"id":73766,"trivia_id":4886,"question":"Which US President declared a \u0027War on Poverty\u0027 in 1964?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:48.000000Z","updated_at":"2022-04-27T08:06:48.000000Z","answers":[{"id":295045,"question_id":73766,"answer":"Johnson","correct":true},{"id":295053,"question_id":73766,"answer":"Nixon","correct":false},{"id":295061,"question_id":73766,"answer":"Truman","correct":false},{"id":295067,"question_id":73766,"answer":"Carter","correct":false}]},{"id":73648,"trivia_id":4886,"question":"What was the name of Eleanor Roosevelt\u0027s President husband?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:46.000000Z","updated_at":"2022-04-27T08:06:46.000000Z","answers":[{"id":294578,"question_id":73648,"answer":"Franklin","correct":true},{"id":294582,"question_id":73648,"answer":"George","correct":false},{"id":294589,"question_id":73648,"answer":"William","correct":false},{"id":294600,"question_id":73648,"answer":"Frederick","correct":false}]},{"id":73784,"trivia_id":4886,"question":"Friedrich Nietzsche was famous in the 19th century in which field?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:48.000000Z","updated_at":"2022-04-27T08:06:48.000000Z","answers":[{"id":295119,"question_id":73784,"answer":"Philosophy","correct":true},{"id":295127,"question_id":73784,"answer":"Medicine","correct":false},{"id":295133,"question_id":73784,"answer":"Opera","correct":false},{"id":295139,"question_id":73784,"answer":"Sculpture","correct":false}]},{"id":73793,"trivia_id":4886,"question":"What was the name of Henry VIII\u0027s first wife, previously married to his brother?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:48.000000Z","updated_at":"2022-04-27T08:06:48.000000Z","answers":[{"id":295156,"question_id":73793,"answer":"Catherine","correct":true},{"id":295163,"question_id":73793,"answer":"Anne","correct":false},{"id":295169,"question_id":73793,"answer":"Jane","correct":false},{"id":295177,"question_id":73793,"answer":"Louise","correct":false}]},{"id":73801,"trivia_id":4886,"question":"An Anglo-American has roots in which European country?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:49.000000Z","updated_at":"2022-04-27T08:06:49.000000Z","answers":[{"id":295188,"question_id":73801,"answer":"United Kingdom","correct":true},{"id":295195,"question_id":73801,"answer":"France","correct":false},{"id":295203,"question_id":73801,"answer":"Germany","correct":false},{"id":295210,"question_id":73801,"answer":"Norway","correct":false}]},{"id":73809,"trivia_id":4886,"question":"Charwoman is an old term for which occupation?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:49.000000Z","updated_at":"2022-04-27T08:06:49.000000Z","answers":[{"id":295222,"question_id":73809,"answer":"Cleaner","correct":true},{"id":295227,"question_id":73809,"answer":"Teacher","correct":false},{"id":295231,"question_id":73809,"answer":"Driver","correct":false},{"id":295237,"question_id":73809,"answer":"Farmer","correct":false}]},{"id":73817,"trivia_id":4886,"question":"Maria Montessori was a pioneer in which field?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:49.000000Z","updated_at":"2022-04-27T08:06:49.000000Z","answers":[{"id":295255,"question_id":73817,"answer":"Education","correct":true},{"id":295262,"question_id":73817,"answer":"Music","correct":false},{"id":295268,"question_id":73817,"answer":"Sculpture","correct":false},{"id":295274,"question_id":73817,"answer":"Medicine","correct":false}]},{"id":73826,"trivia_id":4886,"question":"Which President was nicknamed \u0027Old Man Eloquent\u0027?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:49.000000Z","updated_at":"2022-04-27T08:06:49.000000Z","answers":[{"id":295295,"question_id":73826,"answer":"John Quincy Adams","correct":true},{"id":295298,"question_id":73826,"answer":"Abe Lincoln","correct":false},{"id":295306,"question_id":73826,"answer":"James Madison","correct":false},{"id":295310,"question_id":73826,"answer":"Zachary Taylor","correct":false}]},{"id":73836,"trivia_id":4886,"question":"What did the J stand for in J Edgar Hoover?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:49.000000Z","updated_at":"2022-04-27T08:06:49.000000Z","answers":[{"id":295328,"question_id":73836,"answer":"John","correct":true},{"id":295335,"question_id":73836,"answer":"James","correct":false},{"id":295342,"question_id":73836,"answer":"Julian","correct":false},{"id":295349,"question_id":73836,"answer":"Justin","correct":false}]},{"id":73846,"trivia_id":4886,"question":"Lake Mead was formed in which US state after the building of the Hoover Dam?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:49.000000Z","updated_at":"2022-04-27T08:06:49.000000Z","answers":[{"id":295365,"question_id":73846,"answer":"Nevada","correct":true},{"id":295373,"question_id":73846,"answer":"California","correct":false},{"id":295380,"question_id":73846,"answer":"New Hampshire","correct":false},{"id":295386,"question_id":73846,"answer":"Maine","correct":false}]},{"id":73856,"trivia_id":4886,"question":"Which First Word War battle resulted in 60,000 casualties on the first day of fighting?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295403,"question_id":73856,"answer":"Somme","correct":true},{"id":295411,"question_id":73856,"answer":"Gallipoli","correct":false},{"id":295418,"question_id":73856,"answer":"Amiens","correct":false},{"id":295423,"question_id":73856,"answer":"Crimean","correct":false}]},{"id":73865,"trivia_id":4886,"question":"The Austro-Hungarian Empire was brought to an end by which conflict?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295440,"question_id":73865,"answer":"World War 1","correct":true},{"id":295450,"question_id":73865,"answer":"World War 2","correct":false},{"id":295455,"question_id":73865,"answer":"Spanish Civil War","correct":false},{"id":295464,"question_id":73865,"answer":"Crimean War","correct":false}]},{"id":73875,"trivia_id":4886,"question":"Who explored North America on a boat called the Hopewell?","order":null,"accuracy":null,"verified":null,"created_at":"2022-04-27T08:06:50.000000Z","updated_at":"2022-04-27T08:06:50.000000Z","answers":[{"id":295482,"question_id":73875,"answer":"Hudson","correct":true},{"id":295489,"question_id":73875,"answer":"Pizarro","correct":false},{"id":295492,"question_id":73875,"answer":"Columbus","correct":false},{"id":295496,"question_id":73875,"answer":"Cabot","correct":false}]}]},"answers":[]}"""
window = window.replace('null', 'None')
window = window.replace('false', 'False')
window = window.replace('true', 'True')
window = eval(window)

print(append_questions(window, 'Data\\history.pkl'))
#print()

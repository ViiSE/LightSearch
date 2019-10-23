-- From Authorization test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (1, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss.S'), RAWTOHEX('{"IMEI":"111111111111111","is_done":"True","message":"Test auth!",TK_list:["TK 1", "TK 2"],"sklad_list":["Sklad 1", "Sklad 2", "Sklad 3"]}'), false);

-- From Search test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (2, CURRENT_TIMESTAMP, RAWTOHEX('{"IMEI":"111111111111111","is_done":"True","data":[{"subdiv":"Sklad 1", "ID":"22505", "name":"Item 1", "amount":"10", "price":"200", "ei":"pcs."}]'), false);

-- From OpenSoftCheck test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (3, CURRENT_TIMESTAMP, RAWTOHEX('{"IMEI":"111111111111111","is_done":"True","message":"TEST! SoftCheck is opened!"'), false);

-- From CancelSoftCheck test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (4, CURRENT_TIMESTAMP, RAWTOHEX('{"IMEI":"111111111111111","is_done":"True","message":"TEST! SoftCheck is canceled!"'), false);

-- From CloseSoftCheck test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (5, CURRENT_TIMESTAMP, RAWTOHEX('{"IMEI":"111111111111111","is_done":"True","message":"TEST! SoftCheck is closed!"'), false);

-- From ConfirmSoftCheckProducts test
INSERT INTO LS_RESPONSE(LSCODE, DDOC, CMDOUT, STATE)
    VALUES (6, CURRENT_TIMESTAMP, RAWTOHEX('{"IMEI":"111111111111111","is_done":"True","data":[{"ID":"22505","amount":"7"}]'), false);
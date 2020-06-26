INSERT INTO permition VALUES (1, 'ROLE_CADASTRAR_CIDADE');
INSERT INTO permition VALUES (2, 'ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permition (id_grupo, id_permition) VALUES (1, 1);
INSERT INTO grupo_permition (id_grupo, id_permition) VALUES (1, 2);

INSERT INTO user_grupo (id_user, id_grupo) VALUES (
	(SELECT id FROM user WHERE email = 'admin@cc.com'), 1);

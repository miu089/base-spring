-- 初期設定
-- ユーザーテーブル
CREATE TABLE users (
    user_id SERIAL NOT NULL,
    login_id TEXT NOT NULL,
    password TEXT NOT NULL,
    user_name TEXT,
    created_at TIMESTAMP DEFAULT current_timestamp,
    created_by INTEGER,
    updated_at TIMESTAMP,
    updated_by INTEGER,
    PRIMARY KEY(user_id)
);

CREATE INDEX user_idx1 ON users(login_id);
CREATE INDEX user_idx2 ON users(password);
CREATE INDEX user_idx3 ON users(user_name);

ALTER TABLE users ADD CONSTRAINT user_uniq1 UNIQUE (login_id) ;

COMMENT ON TABLE users IS 'ユーザー';
COMMENT ON COLUMN users.user_id IS 'ID';
COMMENT ON COLUMN users.login_id IS 'ログインID';
COMMENT ON COLUMN users.password IS 'パスワード';
COMMENT ON COLUMN users.user_name IS 'ユーザー名';
COMMENT ON COLUMN users.created_at IS '登録日時';
COMMENT ON COLUMN users.created_by IS '登録者';
COMMENT ON COLUMN users.updated_at IS '更新日時';
COMMENT ON COLUMN users.updated_by IS '更新者';


-- テスト用データ
insert into users (login_id, password, user_name)
values
    ('miu', 'miu', 'miu')
;



CREATE TABLE user_role (
    user_role_id SERIAL NOT NULL,
    user_id INTEGER NOT NULL,
    role_name TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    created_by INTEGER,
    updated_at TIMESTAMP,
    updated_by INTEGER,
    PRIMARY KEY(user_role_id)
);

CREATE INDEX user_role_idx1 ON user_role(user_id);
CREATE INDEX user_role_idx2 ON user_role(role_name);

ALTER TABLE user_role ADD CONSTRAINT user_role_uniq1 UNIQUE (user_id, role_name) ;


COMMENT ON TABLE user_role IS 'ユーザー権限';
COMMENT ON COLUMN user_role.user_role_id IS 'ID';
COMMENT ON COLUMN user_role.user_id IS 'ユーザーID';
COMMENT ON COLUMN user_role.role_name IS '権限名';
COMMENT ON COLUMN user_role.created_at IS '登録日時';
COMMENT ON COLUMN user_role.created_by IS '登録者';
COMMENT ON COLUMN user_role.updated_at IS '更新日時';
COMMENT ON COLUMN user_role.updated_by IS '更新者';

insert into user_role (user_id ,role_name)
values (1, 'ROLE_USER');


-- サンプル画面のためのテーブル
CREATE TABLE test_table (
    test_table_id SERIAL NOT NULL,
    name TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    created_by INTEGER,
    updated_at TIMESTAMP,
    updated_by INTEGER,
    PRIMARY KEY(test_table_id)
);

insert into test_table (name) (
    values
        ('サンプル登録1'),
        ('サンプル登録2'),
        ('サンプル登録3'),
        ('サンプル登録4'),
        ('サンプル登録5')
);




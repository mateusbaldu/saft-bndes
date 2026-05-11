ALTER TABLE operacao_bndes RENAME COLUMN ano TO data_contratacao;

ALTER TABLE operacao_bndes ALTER COLUMN data_contratacao TYPE DATE USING NULL;
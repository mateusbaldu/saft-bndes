ALTER TABLE operacao_bndes
    ADD CONSTRAINT uk_operacao_duplicada UNIQUE (nome_empresa, setor, valor, ano);
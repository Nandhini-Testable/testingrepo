from itertools import combinations


def compativel(v1, v2):
    """
    Duas condições são compatíveis se:
    - têm o mesmo valor; ou
    - pelo menos uma delas é X.
    """
    return v1 == v2 or v1 == "X" or v2 == "X"


def par_mcdc(l1, l2, indice_condicao):
    """
    Verifica se l1 e l2 formam um par MC/DC para uma condição específica.

    l1 e l2 são strings como:
        "VFFX"

    A posição 0 é o resultado da decisão.
    As posições 1..n são as condições.

    indice_condicao é o índice real dentro da string.
    Por exemplo:
        indice_condicao = 1 representa a primeira condição.
        indice_condicao = 2 representa a segunda condição.
    """

    resultado1 = l1[0]
    resultado2 = l2[0]

    # O resultado da decisão precisa mudar
    if resultado1 == resultado2:
        return False

    valor1 = l1[indice_condicao]
    valor2 = l2[indice_condicao]

    # A condição-alvo precisa ter sido avaliada nos dois casos
    if valor1 == "X" or valor2 == "X":
        return False

    # A condição-alvo precisa mudar
    if valor1 == valor2:
        return False

    # As demais condições precisam ser iguais ou compatíveis com X
    for i in range(1, len(l1)):
        if i == indice_condicao:
            continue

        if not compativel(l1[i], l2[i]):
            return False

    return True


def verifica_mcdc(linhas):
    """
    Retorna:
        ok: True/False
        pares: dicionário indicando o par encontrado para cada condição
    """

    if not linhas:
        return False, {}

    tamanho = len(linhas[0])

    # Todas as linhas precisam ter o mesmo tamanho
    for linha in linhas:
        if len(linha) != tamanho:
            raise ValueError("Todas as linhas devem ter o mesmo tamanho.")

    # Verifica caracteres válidos
    for linha in linhas:
        for c in linha:
            if c not in {"V", "F", "X"}:
                raise ValueError(f"Caractere inválido: {c}")

        if linha[0] == "X":
            raise ValueError("O resultado da decisão não pode ser X.")

    numero_condicoes = tamanho - 1
    pares = {}

    for indice_condicao in range(1, numero_condicoes + 1):
        pares[indice_condicao] = None

        for l1, l2 in combinations(linhas, 2):
            if par_mcdc(l1, l2, indice_condicao):
                pares[indice_condicao] = (l1, l2)
                break

        if pares[indice_condicao] is None:
            return False, pares

    return True, pares


def main():
    linhas = []

    while True:
        linha = input().strip().upper()

        if linha == "":
            break

        linhas.append(linha)

    try:
        ok, pares = verifica_mcdc(linhas)

        if ok:
            print("SIM, as linhas formam MC/DC.")
        else:
            print("NÃO, as linhas não formam MC/DC.")

        print()

        for indice, par in pares.items():
            nome_condicao = f"c{indice}"

            if par is None:
                print(f"{nome_condicao}: não coberta")
            else:
                print(f"{nome_condicao}: coberta pelo par {par[0]}  {par[1]}")

    except ValueError as erro:
        print("Erro:", erro)


if __name__ == "__main__":
    main()

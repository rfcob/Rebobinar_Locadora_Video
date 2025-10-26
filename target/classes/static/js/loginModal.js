// Espera o DOM (a página HTML) carregar completamente antes de executar o script
document.addEventListener('DOMContentLoaded', function() {

    // Pega os elementos do DOM
    const modal = document.getElementById('loginModal');
    const openBtn = document.getElementById('openLoginModalBtn');
    const closeBtn = document.getElementById('closeLoginModalBtn');
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');

    // --- Funções para controlar o Modal ---

    // Função para mostrar o modal
    function showModal() {
        if (modal) {
            // 'flex' pois foi definido no CSS para centralizar
            modal.style.display = 'flex';
            loginError.style.display = 'none'; // Esconde erro ao abrir
            document.getElementById('email').focus();
        }
    }

    // Função para fechar o modal
    function closeModal() {
        if (modal) {
            modal.style.display = 'none';
        }
    }

    //Event Listeners

    // Abre o modal ao clicar no botão "Entrar"
    if (openBtn) {
        openBtn.onclick = showModal;
    }

    // Fecha o modal ao clicar no 'X'
    if (closeBtn) {
        closeBtn.onclick = closeModal;
    }

    // Fecha o modal se o usuário clicar fora da "caixa"
    window.onclick = function(event) {
        if (event.target == modal) {
            closeModal();
        }
    }

    // Formulário (AJAX/Fetch)
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();

            loginError.style.display = 'none';

            const formData = new FormData(loginForm);

            fetch('/login', {
                method: 'POST',
                body: new URLSearchParams(formData)
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Falha no login');
                    }
                })
                .then(data => {
                    if (data.success) {
                        window.location.href = data.redirectUrl;
                    }
                })
                .catch(error => {
                    console.error('Erro:', error);
                    loginError.style.display = 'block';
                });
        });
    }
});
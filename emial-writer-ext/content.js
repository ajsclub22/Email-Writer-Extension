console.log("Email writer Extension!")

function findComposeToolbar(){
    const selectors = [
        '.aDh',
        '.btC',
        '[role="toolbar"]',
        '.gU.Up'
    ]
    for(const selector of selectors){
        const toolbar = document.querySelector(selector);
        if(toolbar)
            return toolbar;
        return null;
    }

}

function getEmailContent(){
    const selectors = [
        '.h7',
        '.a3s.aiL',
        '[role="presentation"]',
        '.gmail-quote'
    ]
    for(const selector of selectors){
        const content = document.querySelector(selector);
        if(content)
            return content.innerText.trim();
        return '';
    }

}

function createAIButton() {
    const button = document.createElement('button');
    button.type = 'button';
    button.className = 'ai-reply-button T-I J-J5-Ji aoO v7 T-I-atl L3'; // Gmail blue button styles
    button.innerText = 'AI Reply';
    button.setAttribute('data-tooltip', 'Generate AI reply');
    return button;
}



function injectButton() {
    const existButton = document.querySelector('.ai-reply-button');
    if (existButton) existButton.remove();

    const sendButton = document.querySelector('.T-I.J-J5-Ji.aoO.v7.T-I-atl.L3');
    if (!sendButton || !sendButton.parentElement) {
        console.log('Send button not found!');
        return;
    }

    console.log('Send button found, creating AI Reply button...');
    const button = createAIButton();

    button.addEventListener('click', async () => {
        try {
            button.innerText = 'Generating...';
            button.disabled = true;

            const eContent = getEmailContent();
            const response = await fetch('http://localhost:8080/api/mail/generate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    content: eContent,
                    tone: "professional"
                })
            });

            if (!response.ok) throw new Error('API Request Failed');

            const generatedReply = await response.text();
            const composeBox = document.querySelector('[role="textbox"][g_editable="true"]');
            if (composeBox) {
                composeBox.focus();
                document.execCommand('insertText', false, generatedReply);
            } else {
                console.log('Compose box not found');
            }

        } catch (error) {
            console.error(error);
            alert('Failed to generate reply');
        } finally {
            button.innerText = 'AI Reply';
            button.disabled = false;
        }
    });

    // ✅ Create a clean wrapper to avoid inserting inside the button group
    const wrapper = document.createElement('div');
    wrapper.style.display = 'inline-block';
    wrapper.style.marginLeft = '8px'; // Spacing between Send and AI Reply
    wrapper.appendChild(button);

    // ✅ Insert the wrapper (NOT the button) after Send button
    sendButton.parentElement.parentElement.insertBefore(wrapper, sendButton.parentElement.nextSibling);
}






const observer = new MutationObserver((mutations)=>{
    for(const mutation of mutations){
        const addedNodes = Array.from(mutation.addedNodes);
        const hasComposeElement = addedNodes.some(node => 
            node.nodeType == Node.ELEMENT_NODE && 
            (node.matches('.aDH, .btC, [role="dialog"]') || node.querySelector('.aDh, .btC, [role="dialog"]'))
        );
        if(hasComposeElement){
            console.log("compose window Detected");
            setTimeout(injectButton, 500);
        }
    }
})

observer.observe(document.body, {
    childList: true,
    subtree:true
});
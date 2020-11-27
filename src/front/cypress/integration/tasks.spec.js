context('Assertions', () => {
    beforeEach(() => {
        cy.visit('http://localhost:8080/project/1/tasks');
    });

    it('Should open and close task card', () => {
        cy.get('.card-title').first().click();
        cy.get('.modal-dialog').should('be.visible');
        cy.wait(1000);
        cy.get('app-expanded-task-card > .modal > .modal-dialog > .modal-content >.modal-header > .close').first().click();
        cy.get('.modal-dialog').should('not.be.visible');
    });

    it('Should open and close task creation', () => {
        cy.get('#newbuttontask').first().click();
        cy.get('form').should('be.visible');
        cy.wait(1000);
        cy.get('#newTask > .modal-dialog > .modal-content >.modal-header > .close').first().click();
        cy.get('form').should('not.be.visible');
    });


    it('Should change the name of a task', () => {
        // Open card
        cy.get('.card-title').first().click();
        cy.get('.modal-dialog').should('be.visible');
        cy.wait(1000);

        // Click modify
        cy.get('button').contains("Modifier").click();
        cy.wait(1000);

        // Enter new task name
        let text = Date.now();
        cy.get('.modify > form > div > input[formcontrolname="title"]').first().clear();
        cy.get('.modify > form > div > input[formcontrolname="title"]').first().type(text);

        // Apply
        cy.get('.modify > form > button[type="submit"]').first().click();
        cy.wait(1000);

        cy.get('.modal-dialog').should('not.be.visible');
        cy.get('.card-title').first().should(($e) => {
            expect($e).to.contain(text);
        });
    });

});

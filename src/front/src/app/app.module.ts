import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BacklogComponent} from './components/sections/backlog/backlog.component';
import {TasksComponent} from './components/sections/tasks/tasks.component';
import {PlanificationComponent} from './components/sections/planification/planification.component';
import {SprintActifComponent} from './components/sections/sprint-actif/sprint-actif.component';
import {TestsComponent} from './components/sections/tests/tests.component';
import {ReleasesComponent} from './components/sections/releases/releases.component';
import {DocumentationComponent} from './components/sections/documentation/documentation.component';
import {StatisticsComponent} from './components/sections/statistics/statistics.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {ProjectService} from './services/project.service';
import {HttpClientModule} from '@angular/common/http';
import {ProjectComponent} from './components/project/project.component';
import {ProjectListComponent} from './components/project/project-list/project-list.component';
import {ProjectItemComponent} from './components/project/project-item/project-item.component';
import {SidebarComponent} from './components/sidebar/sidebar.component';
import { TestItemComponent } from './components/sections/tests/test-item/test-item.component';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ExpandedTaskCardComponent } from './components/sections/tasks/expanded-task-card/expanded-task-card.component';
import { SprintComponent } from './components/sections/backlog/sprint/sprint.component';
import { UsCardComponent } from './components/sections/backlog/us-card/us-card.component';
import { TaskCardComponent } from './components/sections/tasks/task-card/task-card.component';
import { TaskContainerComponent } from './components/sections/tasks/task-container/task-container.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MemberListComponent } from './components/sections/planification/member-list/member-list.component';
import { MemberItemComponent } from './components/sections/planification/member-list/member-item/member-item.component';


import { UnplannedComponent } from './components/sections/backlog/unplanned/unplanned.component';
import { SprintFormComponent } from './components/sections/backlog/sprint-form/sprint-form.component';
import { TestFormComponent } from './components/sections/tests/test-form/test-form.component';
import { EditTestFormComponent } from './components/sections/tests/test-item/edit-test-form/edit-test-form.component';
import { UsCreateFormComponent } from './components/sections/backlog/us-create-form/us-create-form.component';
import { UsEditFormComponent } from './components/sections/backlog/us-card/us-edit-form/us-edit-form.component';
import { HowToComponent } from './components/sections/how-to/how-to.component';
@NgModule({
    declarations: [
        AppComponent,
        BacklogComponent,
        TasksComponent,
        PlanificationComponent,
        SprintActifComponent,
        TestsComponent,
        ReleasesComponent,
        DocumentationComponent,
        StatisticsComponent,
        PageNotFoundComponent,
        ProjectComponent,
        ProjectListComponent,
        ProjectItemComponent,
        SidebarComponent,
        TestItemComponent,
        SprintComponent,
        UsCardComponent,
        ExpandedTaskCardComponent,
        TaskCardComponent,
        TaskContainerComponent,
        MemberListComponent,
        MemberItemComponent,
        UnplannedComponent,
        SprintFormComponent,
        TestFormComponent,
        EditTestFormComponent,
        UsCreateFormComponent,
        UsEditFormComponent,
        HowToComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        DragDropModule,
        MatCheckboxModule
    ],
    providers: [
        ProjectService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

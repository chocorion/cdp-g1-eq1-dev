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
import { UsContainerComponent } from './components/sections/backlog/us-container/us-container.component';
import { UsCardComponent } from './components/sections/backlog/us-card/us-card.component';

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
        UsContainerComponent,
        UsCardComponent,
        ExpandedTaskCardComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [
        ProjectService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

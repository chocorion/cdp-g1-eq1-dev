import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BacklogComponent } from './components/sections/backlog/backlog.component';
import { TasksComponent } from './components/sections/tasks/tasks.component';
import { PlanificationComponent } from './components/sections/planification/planification.component';
import { SprintActifComponent } from './components/sections/sprint-actif/sprint-actif.component';
import { TestsComponent } from './components/sections/tests/tests.component';
import { ReleasesComponent } from './components/sections/releases/releases.component';
import { DocumentationComponent } from './components/sections/documentation/documentation.component';
import { StatisticsComponent } from './components/sections/statistics/statistics.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ProjectComponent } from './components/project/project.component';


const routes: Routes = [
  { path: 'backlog'      , component: BacklogComponent },
  { path: 'tasks'        , component: TasksComponent },
  { path: 'planification', component: PlanificationComponent },
  { path: 'sprintActif'  , component: SprintActifComponent },
  { path: 'tests'        , component: TestsComponent },
  { path: 'releases'     , component: ReleasesComponent },
  { path: 'documentation', component: DocumentationComponent },
  { path: 'statistics'   , component: StatisticsComponent },
  { path: 'project'      , component: ProjectComponent},
  { path: ''             , redirectTo: '/project', pathMatch: 'full'},
  { path: '**'           , component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

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
import { HowToComponent } from './components/sections/how-to/how-to.component';


const routes: Routes = [
  { path: 'project/:projectId/backlog'      , component: BacklogComponent },
  { path: 'project/:projectId/tasks'        , component: TasksComponent },
  { path: 'project/:projectId/planification', component: PlanificationComponent },
  { path: 'sprintActif'  , component: SprintActifComponent },
  { path: 'project/:projectId/tests'        , component: TestsComponent },
  { path: 'project/:projectId/releases'     , component: ReleasesComponent },
  { path: 'project/:projectId/documentation', component: DocumentationComponent },
  { path: 'project/:projectId/statistics'   , component: StatisticsComponent },
  { path: 'projects'      , component: ProjectComponent},
  { path: 'how-to'        , component: HowToComponent},
  { path: ''             , redirectTo: '/projects', pathMatch: 'full'},
  { path: '**'           , component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

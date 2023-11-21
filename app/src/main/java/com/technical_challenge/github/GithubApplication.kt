package com.technical_challenge.github

import android.app.Application
import com.technical_challenge.github.di.ApplicationComponent
import com.technical_challenge.github.di.DaggerApplicationComponent
import com.technical_challenge.github.ui.di.GithubProvider

class GithubApplication :
    Application(),
    GithubProvider
{

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .githubApplication(this)
            .applicationContext(this)
            .build()
            .apply {
                inject(this@GithubApplication)
            }
    }

    override fun provideGithubComponent() =
        applicationComponent.plusGithubComponentBuilder().build()

}
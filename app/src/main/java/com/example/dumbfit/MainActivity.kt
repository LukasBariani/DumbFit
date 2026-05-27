package com.example.dumbfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dumbfit.data.local.AppDatabase
import com.example.dumbfit.data.repository.TecnicaRepository
import com.example.dumbfit.data.repository.TreinoRepository
import com.example.dumbfit.ui.screens.tecnica.TecnicaFormScreen
import com.example.dumbfit.ui.screens.tecnica.TecnicaListScreen
import com.example.dumbfit.ui.screens.treino.TreinoFormScreen
import com.example.dumbfit.ui.screens.treino.TreinoListScreen
import com.example.dumbfit.ui.theme.DumbFitTheme
import com.example.dumbfit.viewmodel.TecnicaViewModel
import com.example.dumbfit.viewmodel.TecnicaViewModelFactory
import com.example.dumbfit.viewmodel.TreinoViewModel
import com.example.dumbfit.viewmodel.TreinoViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getInstance(this) }
    private val treinoRepository by lazy { TreinoRepository(database.treinoDao()) }
    private val tecnicaRepository by lazy { TecnicaRepository(database.tecnicaDao()) }

    private val treinoViewModel: TreinoViewModel by viewModels {
        TreinoViewModelFactory(treinoRepository)
    }

    private val tecnicaViewModel: TecnicaViewModel by viewModels {
        TecnicaViewModelFactory(tecnicaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DumbFitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DumbFitAppNavigation(
                        treinoViewModel = treinoViewModel,
                        tecnicaViewModel = tecnicaViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun DumbFitAppNavigation(
    treinoViewModel: TreinoViewModel,
    tecnicaViewModel: TecnicaViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home_tabs" // Agora a tela inicial é o painel de abas
    ) {
        // Tela principal que agrupa as duas listas
        composable("home_tabs") {
            MainTabsScreen(
                treinoViewModel = treinoViewModel,
                tecnicaViewModel = tecnicaViewModel,
                onNovaTreino = { navController.navigate("treino_form") },
                onNovaTecnica = { navController.navigate("tecnica_form") }
            )
        }

        // Formulários abrem por cima em tela cheia
        composable("treino_form") {
            TreinoFormScreen(
                viewModel = treinoViewModel,
                onVoltar = { navController.popBackStack() }
            )
        }
        composable("tecnica_form") {
            TecnicaFormScreen(
                viewModel = tecnicaViewModel,
                onVoltar = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MainTabsScreen(
    treinoViewModel: TreinoViewModel,
    tecnicaViewModel: TecnicaViewModel,
    onNovaTreino: () -> Unit,
    onNovaTecnica: () -> Unit
) {
    var tabSelecionada by remember { mutableIntStateOf(0) }
    val titulos = listOf("Treinos", "Técnicas")

    // 👇 O modificador 'statusBarsPadding()' adiciona o espaço exato da barra do sistema automaticamente!
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Barra de abas Material 3
        TabRow(selectedTabIndex = tabSelecionada) {
            titulos.forEachIndexed { index, titulo ->
                Tab(
                    selected = tabSelecionada == index,
                    onClick = { tabSelecionada = index },
                    text = { Text(titulo, style = MaterialTheme.typography.titleSmall) }
                )
            }
        }

        // Renderiza a lista correspondente à aba selecionada
        when (tabSelecionada) {
            0 -> TreinoListScreen(viewModel = treinoViewModel, onNovaTreino = onNovaTreino)
            1 -> TecnicaListScreen(viewModel = tecnicaViewModel, onNovaTecnica = onNovaTecnica)
        }
    }
}
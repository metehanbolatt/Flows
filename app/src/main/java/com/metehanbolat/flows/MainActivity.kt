package com.metehanbolat.flows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metehanbolat.flows.ui.theme.FlowsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowsTheme {
                val viewModel : MyViewModel by viewModels()
                //FirstScreen(viewModel = viewModel)
                SecondScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FirstScreen(viewModel: MyViewModel) {
    val counter = viewModel.countDownTimerFlow.collectAsState(initial = 10)
    Surface(color = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = counter.value.toString(),
                fontSize = 26.sp,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
fun SecondScreen(viewModel: MyViewModel) {
    val liveDataValue = viewModel.liveData.observeAsState()
    val stateFlowValue = viewModel.stateFlow.collectAsState()
    val sharedFlowValue = viewModel.sharedFlow.collectAsState(initial = "")

    Surface(color = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = liveDataValue.value ?: "",
                    fontSize = 26.sp
                )
                Button(
                    onClick = {
                        viewModel.changeLiveDataValue()
                    }
                ) {
                    Text(text = "LiveData Button")
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    text = stateFlowValue.value,
                    fontSize = 26.sp
                )
                Button(
                    onClick = {
                        viewModel.changeStateFlowValue()
                    }
                ) {
                    Text(text = "StateFlow Button")
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    text = sharedFlowValue.value,
                    fontSize = 26.sp
                )
                Button(
                    onClick = {
                        viewModel.changeSharedFlowValue()
                    }
                ) {
                    Text(text = "SharedFlow Button")
                }
            }
        }
    }

}

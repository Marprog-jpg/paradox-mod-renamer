## Includes

Includes = {
	"constants.fxh"
	"standardfuncsgfx.fxh"
	"shadow.fxh"
}


## Samplers

PixelShader = 
{
	Samplers = 
	{
		DiffuseMap = 
		{
			AddressV = "Clamp"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 0
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		NormalMap = 
		{
			AddressV = "Clamp"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 1
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		DiffuseBottomMap = 
		{
			AddressV = "Clamp"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 2
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		SurfaceNormalMap = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 3
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		ColorOverlay = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 4
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		ColorOverlaySecond = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 5
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		HeightNormal = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 6
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		FoWTexture = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 7
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		FoWDiffuse = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 8
			MipFilter = "Linear"
			MinFilter = "Linear"
		}

		ShadowMap = 
		{
			AddressV = "Wrap"
			MagFilter = "Linear"
			AddressU = "Wrap"
			Index = 9
			MipFilter = "Linear"
			MinFilter = "Linear"
		}


	}
}


## Vertex Structs

VertexStruct VS_INPUT
{
    float4 vPosition   : POSITION;
	float4 vUV_Tangent : TEXCOORD0;
};


VertexStruct VS_OUTPUT
{
    float4 vPosition	    : PDX_POSITION;
	float4 vUV			    : TEXCOORD0;
	float4 vWorldUV_Tangent	: TEXCOORD1;
	float4 vPrePos_Fade		: TEXCOORD2;
	float4 vScreenCoord		: TEXCOORD3;		
	float2 vSecondaryUV		: TEXCOORD4;
};


## Constant Buffers

ConstantBuffer( 1, 32 )
{
	float4x4 ShadowMapTextureMatrix;
	float3 vTimeDirectionSeasonLerp;
}

## Shared Code

## Vertex Shaders

VertexShader = 
{
	MainCode VertexShader
	[[
		VS_OUTPUT main( const VS_INPUT v )
		{
			VS_OUTPUT Out;
			Out.vPosition = float4( v.vPosition.xyz, 1.0f );
			float4 vTmpPos = float4( v.vPosition.xyz, 1.0f );
			Out.vPrePos_Fade = float4(vTmpPos.xyz, 0.0f);
			float4 vDistortedPos = vTmpPos - float4( vCamLookAtDir * 0.05f, 0.0f );
			vTmpPos = mul( ViewProjectionMatrix, vTmpPos );
			
			// move z value slightly closer to camera to avoid intersections with terrain
			float vNewZ = dot( vDistortedPos, float4( GetMatrixData( ViewProjectionMatrix, 2, 0 ), GetMatrixData( ViewProjectionMatrix, 2, 1 ), GetMatrixData( ViewProjectionMatrix, 2, 2 ), GetMatrixData( ViewProjectionMatrix, 2, 3 ) ) );
			Out.vPosition = float4( vTmpPos.xy, vNewZ, vTmpPos.w );
			
			Out.vSecondaryUV = float2(0.0f,0.0f);
			Out.vUV = float4(0.0f,0.0f,0.0f,0.0f);
			Out.vWorldUV_Tangent = float4(0.0f,0.0f,0.0f,0.0f);
			// Output the screen-space texture coordinates
			Out.vScreenCoord.x = ( Out.vPosition.x * 0.5 + Out.vPosition.w * 0.5 );
			Out.vScreenCoord.y = ( Out.vPosition.w * 0.5 - Out.vPosition.y * 0.5 );
		#ifdef PDX_OPENGL
			Out.vScreenCoord.y = -Out.vScreenCoord.y;
		#endif			
			Out.vScreenCoord.z = Out.vPosition.w;
			Out.vScreenCoord.w = Out.vPosition.w;
			
			return Out;
		}
	]]

}


## Pixel Shaders

PixelShader = 
{
	MainCode PixelShader
	[[
		float4 main( VS_OUTPUT In ) : PDX_COLOR
		{
			float4 vFoWColor = GetFoWColor( In.vPrePos_Fade.xyz, FoWTexture);
			clip( 0.99f - vFoWColor.r );
			float vFoW = GetFoW( In.vPrePos_Fade.xyz, vFoWColor, FoWDiffuse );
			float3 vColor = ApplyDistanceFog( float3(0.23f, 0.28f, 0.46f), In.vPrePos_Fade.xyz ) * vFoW;
			return float4( vColor.rgb, 1 );
			//return float4(0.0f, 0.0f, 0.4f, 0.3f);
		}
	]]

}


## Blend States

BlendState BlendState
{
	AlphaTest = no
	WriteMask = "RED|GREEN|BLUE"
	SourceBlend = "src_alpha"
	BlendEnable = no
	DestBlend = "inv_src_alpha"
}

## Rasterizer States

## Depth Stencil States

## Effects

Effect river
{
	VertexShader = "VertexShader"
	PixelShader = "PixelShader"
}